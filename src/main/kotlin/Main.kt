const val filename = "input.txt"
const val testFile = "test.txt"
val input = Reader(if (System.getenv("TEST") == "True") testFile else filename)

@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
    val buffer = input.readLine()
    val mSize = 14
    val window = mutableMapOf<Char, Int>()

    buffer.forEachIndexed{i, c ->
        window[c] = window[c]?.plus(1) ?: 1

        if (i >= mSize){
            if (window[buffer[i-mSize]]!! > 1){
                window[buffer[i-mSize]] = window[buffer[i-mSize]]!! - 1
            } else{
                window.remove(buffer[i-mSize])
            }

            if (window.none { entry -> entry.value > 1 } ){
                println(i+1)
                return
            }
        }
    }
}
