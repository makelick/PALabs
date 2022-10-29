import java.lang.Integer.max

class LDFS(val startState: State) {
    private val goal = State(
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
    fun search(limit: Int, startTime: Long): Result {
        val root = Node(startState, 0)
        return recursiveSearch(root, limit, startTime)
    }

    fun recursiveSearch(node: Node, limit: Int, startTime : Long): Result {
        iterations++
        memoryStateCounter -= 1
        maxMemoryStateCounter = max(maxMemoryStateCounter, memoryStateCounter)
        if (node.state.sameState(goal)) return Result(node, ResultType.SOLUTION)
        if (node.depth >= limit || !Statistic().isEnoughMemory() || !Statistic().isEnoughTime(startTime)) {
            deadEndCounter++
            return Result(node, ResultType.CUTOFF)
        }
        val successors = node.expand()
        memoryStateCounter += successors.size
        totalStateCounter += successors.size
        for (i in successors) {
            val res = recursiveSearch(i, limit, startTime)
            if (res.type == ResultType.SOLUTION) return res
        }
        return Result(node, ResultType.FAIL)
    }
}