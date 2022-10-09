import java.io.BufferedReader
import java.io.File
import java.io.FileReader

fun externalSort(fileName: String, numOfFiles: Int) {
    val inputFile = File(fileName)
    val filesB = List(numOfFiles) { i -> File("B${i + 1}.txt").also { it.writeText("") } }
    val filesC = List(numOfFiles) { i -> File("C${i + 1}.txt").also { it.writeText("") } }

    splitFile(inputFile, filesB, numOfFiles)

    var BC = true

    while (!isFullySorted(File(fileName), filesB[0], filesC[0])) {
        if (BC) {
            merge(filesB, filesC, numOfFiles)
        } else {
            merge(filesC, filesB, numOfFiles)
        }
        BC = !BC
    }

    if (filesB[0].length() == inputFile.length()) {
        filesB[0].copyTo(File("output.txt"), true)
    } else {
        filesC[0].copyTo(File("output.txt"), true)
    }
    for (i in 0 until numOfFiles) {
        filesB[i].writeText("")
        filesC[i].writeText("")
    }
}

fun splitFile(inputFile: File, outputFiles: List<File>, numOfFiles: Int) {
    var set = mutableListOf<Int>()
    val reader = BufferedReader(FileReader(inputFile))
    var counter = 0

    while (!isEOFs(listOf(reader))) {
        val element = reader.readLine().toInt()

        if (set.isEmpty() || element >= set.last()) set.add(element)
        else {
            outputFiles[counter].appendText(set.joinToString(separator = "\n", postfix = "\n"))
            set = mutableListOf(element)
            counter = (counter + 1) % numOfFiles
        }
    }
    outputFiles[counter].appendText(set.joinToString(separator = "\n", postfix = "\n"))
}

fun merge(inputFiles: List<File>, outputFiles: List<File>, numOfFiles: Int) {
    for (file in outputFiles) file.writeText("")
    val buffReaders = List(numOfFiles) { i -> BufferedReader(FileReader(inputFiles[i])) }
    var outputFileCounter = 0
    while (!isEOFs(buffReaders)) {
        val sortedList = mutableListOf<Int>()
        val tempList = mutableListOf<Pair<Int, Int>>()
        for (i in buffReaders.indices) {
            val line = checkLine(buffReaders[i])
            if (line != null && line != "") {
                tempList.add(Pair(i, line.toInt()))
            }
        }
        var flag = true
        while (flag) {
            var minValue = Int.MAX_VALUE
            var minIndex = -1
            if (tempList.isNotEmpty()) {
                var indexForDel = 0
                for (i in tempList.indices) {
                    if (tempList[i].second <= minValue) {
                        minValue = tempList[i].second
                        minIndex = tempList[i].first
                        indexForDel = i
                    }
                }
                sortedList.add(minValue)
                tempList.removeAt(indexForDel)
                buffReaders[minIndex].readLine()
                val line = checkLine(buffReaders[minIndex])
                if (line != null && line != "" && line != "-") {
                    if (sortedList.isEmpty() || line.toInt() >= sortedList.last()) {
                        tempList.add(Pair(minIndex, line.toInt()))
                    }
                }
            } else {
                outputFiles[outputFileCounter].appendText(sortedList.joinToString(separator = "\n", postfix = "\n"))
                sortedList.clear()
                outputFileCounter = (outputFileCounter + 1) % numOfFiles
                flag = false
            }
        }

    }
}

fun checkLine(reader: BufferedReader): String? {
    reader.mark(50)
    val line = reader.readLine()
    reader.reset()
    return line
}

fun isFullySorted(fileA: File, fileB: File, fileC: File): Boolean {
    return fileA.length() == fileB.length() || fileA.length() == fileC.length()
}

fun isEOFs(buffReaders: List<BufferedReader>): Boolean {
    for (i in buffReaders) {
        if (checkLine(i) !in setOf("", null)) return false
    }
    return true
}
