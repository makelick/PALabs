fun main() {
    val startTimeOfGenerating = System.currentTimeMillis()

    val inputFilename = "input.txt"
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

    generateFile(inputFilename, fileLength, blockLength)

    val totalTimeOfGenerating = System.currentTimeMillis() - startTimeOfGenerating
    println("Running time ${"%02d".format(totalTimeOfGenerating / (60_000))}" +
            ":${"%02d".format(totalTimeOfGenerating / 1000 % 60)}" +
            ".${totalTimeOfGenerating % 1000}")
}