import java.io.File

@Suppress("unused")
class Reader(filename: String) {
    private val bufferedReader = File(filename).inputStream().bufferedReader()

    fun readLine(): String = bufferedReader.readLine()
    fun readLines(): List<String> = bufferedReader.readLines()

    fun forEachLine(foo: (input: String) -> Unit) =
        bufferedReader.forEachLine {foo(it)}

    fun readAll(): String = bufferedReader.readText().replace("\r", "")
}