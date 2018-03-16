package codes.jean.telepathy

/**
 * ConnectionInformation holds all information which are needed to send data to the server
 * Created by Jean on 04.03.2018.
 */
class ConnectionInformation{
    companion object {
        var serverIP:String = ""
        var secNum:String = ""

        /**
         * Checks if the qr code string is correct, splits the connection info string from the QR code and sets ip and secure number for the connection
         * @return returns true if regex matches local ip + ; + secure number
         */
        fun splitAndSetConnectionInfos(infos:String) : Boolean{

            val regex = Regex(pattern = """^192\.168(\.(\d|\d{2}|(1[0-9][0-9]|2[0-4][0-9]|25[0-5]))){2}:([3-5][0-9][0-9][0-9][0-9]|6[0-5][0-4][0-9][0-9]);\d{4}$""") //regex matching 192.168.[0-255].[0-255]:[30000-65499];[1000-9999]

            if(regex.matches(infos)) {

                val splitted = infos.split(";")
                serverIP = splitted[0]
                secNum = splitted[1]

                return true
            }

            return false
        }
    }
}