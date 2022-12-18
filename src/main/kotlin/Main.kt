const val filename = "input.txt"
const val testFile = "test.txt"
val input = Reader(if (System.getenv("TEST") == "True") testFile else filename)

@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
    val commands = input.readAll().split("$ ").filterNot{it == ""}.map{ it.split('\n').filterNot{ it == ""}}

    val pwd = mutableListOf<String>()
    val root = Directory("/", null, 0)
    var curDir = root

    commands.forEach { entry ->
        val command = entry.first().split(" ")

        if (command.first() == "cd"){
            val dest = command[1]

            curDir = when (dest) {
                ".." -> {
                    curDir.parent ?: root
                }
                "/" -> {
                    root
                }
                else -> {
                    curDir.subs[dest]!!
                }
            }
        } else if (command.first() == "ls"){
            entry.drop(1).map { listItem ->
                if (listItem.startsWith("dir")){
                    val dirName = listItem.split(' ').last()
                    curDir.subs[dirName] = Directory(dirName, curDir, curDir.level + 1)
                } else {
                    val fileInfo = listItem.split(' ')
                    val newFile = File(fileInfo[1], fileInfo[0].toInt())
                    curDir.files[newFile.name] = newFile
                }
            }
        }
    }

    root.sizeUp()
    root.display()

    val spaceNeeded = 30000000 - (70000000 - root.size)

    val all = mutableSetOf<Directory>()
    root.listAll(all)
    println(all.filter { it.size >= spaceNeeded }.minBy { it.size }.size)
}
