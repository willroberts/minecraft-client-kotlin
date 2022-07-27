package com.github.willroberts.minecraftclient

import java.nio.ByteBuffer
import java.nio.ByteOrder

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
    var buf: ByteBuffer = ByteBuffer.allocate(msg.length + 4)
    buf.order(ByteOrder.LITTLE_ENDIAN)
    buf.putInt(msg.length)
    buf.putInt(msg.id)
    buf.putInt(msg.type.value)
    buf.put(msg.body.toByteArray())
    buf.put(byteArrayOf(0, 0))
    buf.flip()

    var bytes: ByteArray = ByteArray(msg.length + 4)
    buf.get(bytes, 0, bytes.size)

    return bytes
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