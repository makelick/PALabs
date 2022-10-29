abstract class Algorithm {
    val goal = State(
        listOf(
            mutableListOf(0, 1, 2),
            mutableListOf(3, 4, 5),
            mutableListOf(6, 7, 8),
        )
    )
    var iterations = 0
    var totalStateCounter = 0
    var memoryStateCounter = 1
    var maxMemoryStateCounter = memoryStateCounter
    var deadEndCounter = 0

    fun isEnoughMemory(): Boolean {
        val runtime = Runtime.getRuntime()
        val usedMemInMB = (runtime.totalMemory() - runtime.freeMemory()) / 1_048_576L
        return usedMemInMB <= 1024
    }

    fun isEnoughTime(startTime: Long): Boolean {
        val totalTime = System.currentTimeMillis() - startTime
        return totalTime / 60_000 < 30
    }

    fun printStats(startTime: Long) {
        println(
            "Iterations: $iterations\n" +
                    "Dead ends: $deadEndCounter\n" +
                    "Total states: $totalStateCounter\n" +
                    "Max states in memory: $maxMemoryStateCounter"
        )
        val totalTime = System.currentTimeMillis() - startTime
        println(
            "Total time ${"%02d".format(totalTime / (60_000))}" +
                    ":${"%02d".format(totalTime / 1000 % 60)}" +
                    ".${totalTime % 1000}"
        )
    }
}