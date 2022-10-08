import java.io.BufferedReader
import java.io.File
import java.io.FileReader
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
            filesB[counter].appendText(set.joinToString(separator = "\n", postfix = "\n"))
            set = mutableListOf(element)
            counter++
            if (counter >= numOfFiles) counter %= numOfFiles
        }
    }
    filesB[counter].appendText(set.joinToString(separator = "\n", postfix = "\n"))


    var BC = true

    while (!isFullySorted(File(fileName), filesB[0], filesC[0])) {
        if (BC) {
            for (c in filesC) c.writeText("")
            val buffReadersB = List(numOfFiles) { i -> BufferedReader(FileReader(filesB[i])) }
            var outputFile = 0
            while (!isEOFs(buffReadersB)) {
            val sortedList = mutableListOf<Int>()
            val tempList = mutableListOf<Pair<Int, Int>>()
            for (i in buffReadersB.indices) {
                val line = checkLine(buffReadersB[i])
                if ( line != null && line != "") {
                    tempList.add(Pair(i, line.toInt()))
                }
            }
                while (true) {
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
                        buffReadersB[minIndex].readLine()
                        val line = checkLine(buffReadersB[minIndex])
                        if (line != null && line != "" && line != "-") {
                            if (sortedList.isEmpty() || line.toInt() >= sortedList.last()) {
                                tempList.add(Pair(minIndex, line.toInt()))
                            }
                        }
                    } else {
                        filesC[outputFile].appendText(sortedList.joinToString(separator = "\n", postfix = "\n"))
                        sortedList.clear()
                        outputFile = (outputFile + 1) % numOfFiles
                        break
                    }
                }

            }
        } else {
            for (b in filesB) b.writeText("")
            val buffReadersC = List(numOfFiles) { i -> BufferedReader(FileReader(filesC[i])) }
            var outputFile = 0
            while (!isEOFs(buffReadersC)) {
                val sortedList = mutableListOf<Int>()
                val tempList = mutableListOf<Pair<Int, Int>>()
                for (i in buffReadersC.indices) {
                    val line = checkLine(buffReadersC[i])
                    if ( line != null && line != "") {
                        tempList.add(Pair(i, line.toInt()))
                    }
                }
                while (true) {
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
                        buffReadersC[minIndex].readLine()
                        val line = checkLine(buffReadersC[minIndex])
                        if (line != null && line != "" && line != "-") {
                            if (sortedList.isEmpty() || line.toInt() >= sortedList.last()) {
                                tempList.add(Pair(minIndex, line.toInt()))
                            }
                        }
                    } else {
                        filesB[outputFile].appendText(sortedList.joinToString(separator = "\n", postfix = "\n"))
                        sortedList.clear()
                        outputFile = (outputFile + 1) % numOfFiles
                        break
                    }
                }

            }
        }
        BC = !BC
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
