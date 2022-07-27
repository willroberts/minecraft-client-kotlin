# minecraft-client-kotlin

[![License Badge]][License]

A client for the Minecraft RCON protocol.

## Library Usage

```kotlin
try {
    val client = MinecraftRCONClient("127.0.0.1", 25575)
} catch(exception: Exception) { /* Failed to connect. */ }

try {
    client.authenticate("minecraft")
} catch(exception: Exception) { /* Failed to log in. */ }

try {
    val resp: Message = client.sendCommand("seed")
    println("${resp.body}") // "Seed: [5454567064266725003]"
} catch(exception: Exception) { /* Failed to send command. */ }
```

## Starting a server for testing

```
$ docker pull itzg/minecraft-server
$ docker run --name=minecraft-server -p 25575:25575 -d -e EULA=TRUE itzg/minecraft-server
```

## Running Tests

```bash
$ gradle test
```

## Reference

- https://wiki.vg/Rcon

[License]: https://www.gnu.org/licenses/gpl-3.0
[License Badge]: https://img.shields.io/badge/License-GPLv3-blue.svg