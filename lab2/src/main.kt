fun main() {
    val algo : Algorithm
    val result : Result
    val limit = 22

    val startState = Generator().generateState()
    println("Start state:\n$startState")

    val input = inputAlgo()

    val startTime = System.currentTimeMillis()

    if (input == "LDFS") {
        algo = LDFS(startState)
        result = algo.search(limit, startTime)
    } else {
        algo = AStar(startState)
        result = algo.search(startTime)
    }

    if (result.type == ResultType.SOLUTION) result.node.printSolution()
    else println(result.type)

    algo.printStats(startTime)
}

fun inputAlgo() : String {
    var input: String
    do {
        print("Enter algorithm type (a*/ldfs): ")
        input = readLine()!!.uppercase()
    } while (input != "A*" && input != "LDFS")
    return input
}