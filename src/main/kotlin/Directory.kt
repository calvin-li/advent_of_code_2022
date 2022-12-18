data class File(val name: String, val size: Int)

class Directory(private val name: String, val parent: Directory?, val level: Int) {
    val subs = mutableMapOf<String, Directory>()
    val files = mutableMapOf<String, File>()
    internal var size: Int = 0

    fun display(){
        (0 until this.level).forEach { _ -> print("--") }
        println(this.name + ' ' + this.size)

        this.files.forEach { file ->
            (0 until this.level).forEach { _ -> print("  ") }
            println(file.key + ' ' + file.value.size)
        }

        this.subs.forEach {
            it.value.display()
        }
    }

    fun sizeUp(){
        this.files.forEach { file ->
            this.size += file.value.size
        }

        this.subs.forEach {
            it.value.sizeUp()
            this.size += it.value.size
        }
    }

    fun listAll(all: MutableSet<Directory>){
        this.subs.forEach {
            it.value.listAll(all)
            this.subs.values.map{ k -> all.add(k)}
        }
    }

}