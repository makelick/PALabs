import java.io.File
import java.util.*

fun externalSort(fileName: String, numOfFiles: Int) {
    val filesB = mutableListOf<File>()
    val filesC = mutableListOf<File>()
    for (i in 0 until numOfFiles) {
        filesB.add(File("B${i + 1}.txt").also { it.writeText("") })
        filesC.add(File("C${i + 1}.txt").also { it.writeText("") })
    }

    var set = mutableListOf<Int>()
    val scanner = Scanner(File(fileName))
    var counter = 0

    while (scanner.hasNext()) {
        val element = scanner.nextInt()

        if (set.isEmpty() || element >= set.last()) set.add(element)
        else {
            filesB[counter].appendText(set.joinToString(postfix = "\n"))
            set = mutableListOf(element)
            counter++
            if (counter >= numOfFiles) counter %= numOfFiles
        }
    }
    filesB[counter].appendText(set.joinToString(postfix = "\n"))
}