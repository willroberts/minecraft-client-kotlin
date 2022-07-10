package com.github.willroberts.minecraftclient

// 4-byte request ID, 4-byte message type, 2-byte terminator.
const val HEADER_SIZE: Int = 10

// MessageType is an Int representing the type of message being sent or received.
enum class MessageType(val value: Int) {
    // Returned by the server.
    RESPONSE(0),

    // Used when sending commands to the server.
    COMMAND(2),

    // Used when logging into the server.
    AUTHENTICATE(3)
}

// Message contains fields for RCON messages.
class Message {
    var length = 0
    var id = 0
    var type = MessageType.valueOf("RESPONSE")
    var body = ""
}

// EncodeMessage serializes an RCON command.
// Format: [4-byte message size | 4-byte message ID | 4-byte message type | variable length message | 2-byte terminator].
fun encodeMessage(msg: Message): ByteArray {
    return encoded = byteArrayOf(
        msg.length.toByte(), // OK!
        msg.id.toByte(), // OK!
        msg.type.value.toByte(), // OK!
    ) + msg.body.toByteArray() + byteArrayOf(0, 0)
}

// Testing for now.
fun main() {
    var msg = Message()
    msg.id = 1
    msg.type = MessageType.COMMAND
    msg.body = "seed"
    msg.length = msg.body.length + HEADER_SIZE

    val enc = encodeMessage(msg)
    for (b in enc) println("byte: $b")
}
