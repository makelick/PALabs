import java.lang.Integer.max

class LDFS(val startState: State) {
    private val goal = State(
        listOf(
            mutableListOf(0, 1, 2),
            mutableListOf(3, 4, 5),
            mutableListOf(6, 7, 8),
        )
    )
    var iterations = 0L
    var totalStateCounter = 0L
    var memoryStateCounter = 1
    var maxMemoryStateCounter = memoryStateCounter
    var deadEndCounter = 0L
    fun search(limit: Int): Result {
        val root = Node(startState, 0)
        return recursiveSearch(root, limit)
    }

    fun recursiveSearch(node: Node, limit: Int): Result {
        iterations++
        memoryStateCounter -= 1
        maxMemoryStateCounter = max(maxMemoryStateCounter, memoryStateCounter)
        if (node.state.sameState(goal)) return Result(node, ResultType.SOLUTION)
        if (node.depth >= limit) {
            deadEndCounter++
            return Result(node, ResultType.CUTOFF)
        }
        val successors = node.expand()
        memoryStateCounter += successors.size
        totalStateCounter += successors.size
        for (i in successors) {
            val res = recursiveSearch(i, limit)
            if (res.type == ResultType.SOLUTION) return res
        }
        return Result(node, ResultType.FAIL)
    }

    fun printStats() {
        println("Iterations: $iterations\n" +
                "Dead ends: $deadEndCounter\n" +
                "Total states: $totalStateCounter\n" +
                "Max states in memory: $maxMemoryStateCounter")
    }
}