package codes.jean.telepathy

/**
 * Enum which holds the HTTP property names which are used to set properties
 * Created by Jean on 05.03.2018.
 */
enum class HTTPPropertyNames constructor(private val value: String) {
    SECURE_NUMBER("secNum"), PHONE_NAME("phoneName"), URL_TO_OPEN("urlToOpen"), FILE_NAME("fileName");

    override fun toString(): String {
        return value
    }
}