fun main() {
    val inputFileName = "input.txt"
    var fileLength: ULong = 0uL
    var blockLength = 0

    when (inputFileType()) {
        "small" -> {
            fileLength = 10uL * 1024uL * 1024uL
            blockLength = 500
        }
        "normal" -> {
            fileLength = 500uL * 1024uL * 1024uL
            blockLength = 50_000
        }
        "large" -> {
            fileLength = 8uL * 1024uL * 1024uL * 1024uL
            blockLength = 500_000
        }
    }

    val startGeneratingTime = System.currentTimeMillis()

    generateFile(inputFileName, fileLength, blockLength)

    val startSortingTime = System.currentTimeMillis()
    val totalTimeOfGenerating = startSortingTime - startGeneratingTime
    println("Generating time ${"%02d".format(totalTimeOfGenerating / (60_000))}" +
            ":${"%02d".format(totalTimeOfGenerating / 1000 % 60)}" +
            ".${totalTimeOfGenerating % 1000}")

    externalSort(inputFileName,  3)

    val totalSortingTime = System.currentTimeMillis() - startSortingTime
    println("Sorting time ${"%02d".format(totalSortingTime / (60_000))}" +
            ":${"%02d".format(totalSortingTime / 1000 % 60)}" +
            ".${"%03d".format(totalSortingTime % 1000)}")
}