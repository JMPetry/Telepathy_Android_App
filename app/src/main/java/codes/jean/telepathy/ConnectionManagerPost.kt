package codes.jean.telepathy

import android.os.AsyncTask
import java.io.*
import java.net.HttpURLConnection
import java.net.URL


/**
 * ConnectionManagerPost creates post requests for sending files
 * Created by Jean on 06.03.2018.
 */
class ConnectionManagerPost : AsyncTask<InputStream, InputStream, String>() {

    var fileName = "error"
    private val BUFFER_SIZE = 4096
    private val BYTE_ARRAY_OUTPUT_STREAM_SIZE = 8000

    /**
     * Creating and sending a post request with binary file data
     */
    override fun doInBackground(vararg p0: InputStream?): String {

        val url = URL("http://${ConnectionInformation.serverIP}${HTTPRoutes.SEND_FILE}")
        val conn = url.openConnection() as HttpURLConnection

        //settting up post request and file name/secure number properties
        conn.requestMethod = "POST"
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=12315")
        conn.setRequestProperty(HTTPPropertyNames.FILE_NAME.toString(), fileName)
        conn.setRequestProperty(HTTPPropertyNames.SECURE_NUMBER.toString(), ConnectionInformation.secNum)

        conn.doOutput = true

        //creating a byte array for binary file data
        val availableBytes:ByteArray

        val buffer = ByteArray(BUFFER_SIZE)
        val outs = ByteArrayOutputStream(BYTE_ARRAY_OUTPUT_STREAM_SIZE)

        var read = p0[0]!!.read(buffer)

        while (read  != -1) {
            outs.write(buffer, 0, read)
            read = p0[0]!!.read(buffer)
        }

        p0[0]!!.close()
        outs.close()
        availableBytes = outs.toByteArray()

        //sending the post request, writing the file data into the request
        val wr = DataOutputStream(conn.outputStream)
        wr.write(availableBytes)
        wr.flush()
        wr.close()

        //getting the response
        val responseCode = conn.responseCode
        println("Response Code : " + responseCode)

        val `in` = BufferedReader(InputStreamReader(conn.inputStream))
        val response = StringBuffer()

        var inputLine = `in`.readLine()

        while (inputLine  != null) {
            response.append(inputLine)
            inputLine = `in`.readLine()
        }
        `in`.close()

        return response.toString()
    }
}