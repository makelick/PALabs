import java.io.File
import kotlin.random.Random

fun inputFileType() : String {
    var fileType : String
    print(
        "Enter length of the file (small/normal/large) " +
                "\nSmall - 10 MB " +
                "\nNormal - 500 MB " +
                "\nLarge - 8 GB \n"
    )
    do {
        fileType = readln().lowercase()
    } while (!setOf("small", "normal", "large").contains(fileType))

    return fileType
}

fun generateFile(name: String, length: ULong, blockLength : Int) {
    val file = File(name)
    file.writeText("")
    do {
        val tempList = MutableList(blockLength) {Random.nextInt()}
        file.appendText(tempList.joinToString(separator = " "))
    } while(file.length().toULong() < length)
}