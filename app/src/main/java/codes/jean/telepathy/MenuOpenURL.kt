package codes.jean.telepathy

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.Window
import kotlinx.android.synthetic.main.activity_menu_open_url.*
import android.util.DisplayMetrics
import android.content.Intent

/**
 * MenuOpenUrl provides the layout of the scene where an URL can be send to the server
 */
class MenuOpenURL : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //hide top bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_menu_open_url)

        //size menu to fit screen size
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels

        menu.layoutParams.height = height

        //set right menu button for activity
        bFileMenu.setTextColor(Color.parseColor("#969696"))
        bUrlMenu.setTextColor(Color.parseColor("#ffffff"))

        //change activities on button press
        bFileMenu.setOnClickListener {
            val switchToSendFile = Intent(this, MenuSendFile::class.java)
            startActivity(switchToSendFile)
            overridePendingTransition(0, 0)
        }

        //send request to server with url to open taken from textInput eTURL
        bSendURL.setOnClickListener {

            val e = ConnectionManagerGet()
            e.urlToOpen = eTURL.text.toString()

            if (eTURL.text.toString() != "") {
                e.execute(HTTPRoutes.OPENURL.toString())
            } else {
                eTURL.hint = getString(R.string.hintSetUrlEmpty)
            }
        }
    }
}
