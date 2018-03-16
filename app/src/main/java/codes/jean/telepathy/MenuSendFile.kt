package codes.jean.telepathy

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.DisplayMetrics
import android.util.Log
import android.view.Window
import kotlinx.android.synthetic.main.activity_menu_send_file.*
import android.webkit.MimeTypeMap


/**
 * Class for managing files, getting their names and sending the file
 */
class MenuSendFile : Activity() {

    //path to a file which was selected
    private var fileUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //hide top bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_menu_send_file)

        //size menu to fit screen size
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels

        menuSF.layoutParams.height = height

        //set right buttons 'active'
        bUrlMenuSF.setTextColor(Color.parseColor("#969696"))
        bFileMenuSF.setTextColor(Color.parseColor("#ffffff"))

        bChooseFile.setOnClickListener{
            performFileSearch()
        }

        bSendFile.setOnClickListener{

            if(fileUri == null){
                tVFilePath.text = getString(R.string.tVNoFileWasChosen)
            }else{

                tVFilePath.text = getString(R.string.tVSendingFilePath)
                val inputStream = contentResolver.openInputStream(fileUri)

                val fileExtension = MimeTypeMap.getFileExtensionFromUrl(fileUri.toString())
                val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension)

                print(mimeType)

                //starting to send a post request by calling the connection manager with the URI of the selected file
                val requestPost = ConnectionManagerPost()
                requestPost.fileName = getFileName(fileUri!!)
                requestPost.execute(inputStream)

                fileUri = null
            }
        }

        //switching to MenuOpenURL
        bUrlMenuSF.setOnClickListener{
            val switchToSendUrl = Intent(this, MenuOpenURL::class.java)
            switchToSendUrl.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(switchToSendUrl)
            overridePendingTransition(0, 0)
        }

    }

    private val READ_REQUEST_CODE = 42
    /**
     * Shows up the FileChooser UI and shows files of all types
     */
    private fun performFileSearch() {

        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)

        //only getting openable files(no contacts...)
        intent.addCategory(Intent.CATEGORY_OPENABLE)

        //type is */* to get all files without checking their format
        intent.type = "*/*"

        startActivityForResult(intent, READ_REQUEST_CODE)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int,
                                         resultData: Intent?) {

        //getting the uri of the selected file
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            if (resultData != null) {
                fileUri = resultData.data
                Log.i(TAG, "Uri: " + fileUri!!.toString())

                if(fileUri != null){
                    tVFilePath.text = getFileName(fileUri!!)
                }
            }
        }
    }

    /**
     * Gets the name of the file with a specific Uri
     */
    private fun getFileName(uri: Uri): String {

        val cursor = this.contentResolver.query(uri, null, null, null, null, null)
        var displayName = "Unknown Filename"

        try{
            if (cursor != null && cursor.moveToFirst()) {
                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
        } finally {
            cursor.close()
        }

        return displayName
    }
}
