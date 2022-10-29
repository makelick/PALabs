class Statistic {
    fun isEnoughMemory() : Boolean {
        val runtime = Runtime.getRuntime()
        val usedMemInMB = (runtime.totalMemory() - runtime.freeMemory()) / 1_048_576L
        return usedMemInMB <= 1024
    }

    fun isEnoughTime(startTime: Long) : Boolean {
        val totalTime = System.currentTimeMillis() - startTime
        return totalTime / 60_000 < 30
    }

    fun printStats(iterations : Int, deadEndCounter : Int, totalStateCounter : Int, maxMemoryStateCounter : Int) {
        println("Iterations: $iterations\n" +
                "Dead ends: $deadEndCounter\n" +
                "Total states: $totalStateCounter\n" +
                "Max states in memory: $maxMemoryStateCounter")
    }
}