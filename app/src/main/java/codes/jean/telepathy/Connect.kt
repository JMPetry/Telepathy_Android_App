package codes.jean.telepathy

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import android.util.Log
import android.widget.Toast

/**
 * Opens the camera to scan a QR code to connect to the server
 */
class Connect : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect)

        //setup for reading a QR code
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt(getString(R.string.readQRText))
        integrator.setCameraId(0)
        integrator.setOrientationLocked(false)
        integrator.setBeepEnabled(false)
        integrator.setBarcodeImageEnabled(false)
        integrator.initiateScan()
    }

    /**
     * fetching result from scan
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

         //getting the result from qr code scan, setting it in a singleton for later use
         val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
         if (result != null) {
            if (result.contents == null) {
                Log.d("MainActivity", "Cancelled scan")
            } else {
                Log.d("MainActivity", "Scanned")

                if(ConnectionInformation.splitAndSetConnectionInfos(result.contents)) { //splitting the connection infos and the secure number and setting them in a data class

                    //setting up Intent to switch to next scene
                    val switchToUrlMenu = Intent(this, MenuOpenURL::class.java)

                    val con = ConnectionManagerGet()
                    con.phoneName = android.os.Build.MANUFACTURER.toUpperCase() + " " + android.os.Build.MODEL //getting the phone name which will be send to the server
                    con.execute(HTTPRoutes.START.toString())

                    startActivity(switchToUrlMenu) //switching to MenuOpenURL
                }else{
                    Toast.makeText(applicationContext, getString(R.string.wrongQRCodeScanned), Toast.LENGTH_LONG).show()
                    finish()
                    startActivity(intent)
                }
            }
         }else{
            super.onActivityResult(requestCode, resultCode, data)
         }
    }

}
