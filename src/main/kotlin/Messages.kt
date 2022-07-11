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
    AUTHENTICATE(3);

    companion object {
        private val VALUES = values()
        fun fromInt(value: Int) = VALUES.first { it.value == value }
    }
}

// Message contains fields for RCON messages.
class Message {
    var length = 0
    var id = 0
    var type = MessageType.RESPONSE
    var body = ""
}

// encodeMessage serializes an RCON command.
// Format: [4-byte message size | 4-byte message ID | 4-byte message type | variable length message | 2-byte terminator].
fun encodeMessage(msg: Message): ByteArray {
    return byteArrayOf(
        msg.length.toByte(),
        msg.id.toByte(),
        msg.type.value.toByte(),
    ) + msg.body.toByteArray() + byteArrayOf(0, 0)
}

// decodeMessage deserialize an RCON response.
// Format: [4-byte message size | 4-byte message ID | 4-byte message type | variable length message].
fun decodeMessage(msg: ByteArray): Message {
    var resp = Message()

    resp.length = parseInt(msg.sliceArray(IntRange(0, 3)))
    resp.id = parseInt(msg.sliceArray(IntRange(4, 7)))
    resp.type = MessageType.fromInt(parseInt(msg.sliceArray(IntRange(8, 11))))
    resp.body = String(msg.sliceArray(IntRange(12, resp.length+1)))

    return resp
}

// parseInt reads 32 little-endian bits into an Int.
fun parseInt(b: ByteArray): Int {
    var result = 0
    for (i in b.indices) {
        result = result or (b[i].toInt() shl 8 * i)
    }
    return result
}

// Testing for now.
fun main() {
    // Test encodeMessage().
    var msg = Message()
    msg.id = 1
    msg.type = MessageType.COMMAND
    msg.body = "seed"
    msg.length = msg.body.length + HEADER_SIZE
    encodeMessage(msg)

    // Test decodeMessage():
    decodeMessage(byteArrayOf(
		// Response length: 38 bytes.
		38, 0, 0, 0,
		// Request ID: 2.
		2, 0, 0, 0,
		// Response type: 0 (msgResponse).
		0, 0, 0, 0,
		// Message: "Seed: [-2474125574890692308]".
		83, 101, 101, 100, 58, 32, 91, 45, 50, 52, 55, 52, 49, 50, 53, 53, 55, 52, 56, 57, 48, 54, 57, 50, 51, 48, 56, 93
    ))
}
