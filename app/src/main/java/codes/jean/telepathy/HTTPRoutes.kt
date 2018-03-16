package codes.jean.telepathy

/**
 * Enum which holds the HTTP routes which are used to communicate with the server
 * Created by Jean on 05.03.2018.
 */
enum class HTTPRoutes private constructor(private val value: String) {
    OPENURL("/openTab"), START("/start"), SEND_FILE("/sendFile");

    override fun toString(): String {
        return value
    }
}

