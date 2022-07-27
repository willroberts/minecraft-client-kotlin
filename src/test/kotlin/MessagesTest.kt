package com.github.willroberts.minecraftclient

import org.junit.Test
import kotlin.collections.contentEquals
import kotlin.test.assertEquals

class MessageEncoderTest {
    @Test
    fun testEncodeMessage() {
        var msg = Message()
        msg.id = 1
        msg.type = MessageType.COMMAND
        msg.body = "seed"
        msg.length = msg.body.length + HEADER_SIZE
        val output = encodeMessage(msg)

        val expected = byteArrayOf(
		    // Request length: 14 bytes.
		    14, 0, 0, 0,
		    // Request ID: 1.
		    1, 0, 0, 0,
		    // Request type: 2 (MessageType.COMMAND).
		    2, 0, 0, 0,
		    // Message: "seed".
		    115, 101, 101, 100,
		    // Terminator.
		    0, 0,            
        )
        assertEquals(expected.contentEquals(output), true)
    }

    @Test
    fun testDecodeMessage() {
        val output = decodeMessage(byteArrayOf(
            // Response length: 38 bytes.
            38, 0, 0, 0,
            // Request ID: 2.
            2, 0, 0, 0,
            // Response type: 0 (MessageType.RESPONSE).
            0, 0, 0, 0,
            // Message: "Seed: [-2474125574890692308]".
            83, 101, 101, 100, 58, 32, 91, 45, 50, 52, 55, 52, 49, 50, 53, 53, 55, 52, 56, 57, 48, 54, 57, 50, 51, 48, 56, 93
        ))
        assertEquals(2, output.id)
        assertEquals(MessageType.RESPONSE, output.type)
        assertEquals("Seed: [-2474125574890692308]", output.body)
        assertEquals(38, output.length)
    }
}