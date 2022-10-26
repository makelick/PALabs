fun main() {
    val limit = 22
    val startState = Generator().generateState()
    println("Start state:\n$startState")
    val startTime = System.currentTimeMillis()
    val ldfs = LDFS(startState)
    val result = ldfs.search(limit)
    if (result.type == ResultType.SOLUTION) result.node.printSolution()
    else println(result.type)

    val totalTime = System.currentTimeMillis() - startTime
    println(
        "Total time ${"%02d".format(totalTime / (60_000))}" +
                ":${"%02d".format(totalTime / 1000 % 60)}" +
                ".${totalTime % 1000}"
    )
}