# minecraft-client-kotlin

[![License Badge]][License]

A client for the Minecraft RCON protocol.

## Library Usage

```kotlin
TBD
```

## Shell Utility

If you are looking for a tool rather than a library, try the shell command:

```
TBD
```

## Limitations

Response bodies over 4KB will be truncated.

## Starting a server for testing

```
$ docker pull itzg/minecraft-server
$ docker run --name=minecraft-server -p 25575:25575 -d -e EULA=TRUE itzg/minecraft-server
```

## Running Tests

```
TBD
```

## Reference

- https://wiki.vg/Rcon

[License]: https://www.gnu.org/licenses/gpl-3.0
[License Badge]: https://img.shields.io/badge/License-GPLv3-blue.svg