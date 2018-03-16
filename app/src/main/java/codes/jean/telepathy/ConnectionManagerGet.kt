package codes.jean.telepathy

import android.os.AsyncTask
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * The ConnectionManagerGet creates http requests with get and post and sends them to the server
 * Created by Jean on 03.03.2018.
 */
class ConnectionManagerGet : AsyncTask<String, String, String>() {

    var urlToOpen = ""
    var phoneName = ""
    private var serverIP = ConnectionInformation.serverIP

    /**
     * Creating, sending and receiving a http request, args are strings(urlroute)
     */
    override fun doInBackground(vararg p0: String?): String {

        val urlRoute = p0[0]

        val url = "http://$serverIP$urlRoute"
        val obj = URL(url)
        val response = StringBuffer()

        val con = obj.openConnection() as HttpURLConnection

        con.requestMethod = "GET"

        //adding the secure number, phone name and url to open to send them in properties to the server
        con.addRequestProperty(HTTPPropertyNames.SECURE_NUMBER.toString(), ConnectionInformation.secNum)
        con.addRequestProperty(HTTPPropertyNames.PHONE_NAME.toString(), phoneName)
        con.addRequestProperty(HTTPPropertyNames.URL_TO_OPEN.toString(), urlToOpen)

        with(con) {

            try {
                    //reading the http response
                    BufferedReader(InputStreamReader(inputStream)).use {

                        var inputLine = it.readLine()

                        while (inputLine != null) {
                            response.append(inputLine + "\n")
                            inputLine = it.readLine()
                        }
                    }

            } catch (e: IOException) {
                print(e.stackTrace)
            }

            print("\nSending $requestMethod request to URL : $url")
            print("Response Code : $responseCode")
            print("ResponseText: $responseMessage")
        }
        return response.toString()
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
    }

}
