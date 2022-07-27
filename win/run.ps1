kotlinc src/main -include-runtime -d minecraft-client.jar
if ($LASTEXITCODE -ne 0) {
    throw 'build failed'
}
java -jar minecraft-client.jar