// MessageType is an Int representing the type of message being sent or received.
enum class MessageType(val t: Int) {
    // Returned by the server.
    RESPONSE(0),

    // Used when sending commands to the server.
    COMMAND(2),

    // Used when logging into the server.
    AUTHENTICATE(3)
}

// Message contains fields for RCON messages.
class Message {
    var length: Int
    var id: Int
    var type: MessageType
    var body: String
}

const val headerSize: Int = 10
