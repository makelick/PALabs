import java.lang.Integer.max

class LDFS(val startState: State) : Algorithm() {
    fun search(limit: Int, startTime: Long): Result {
        val root = Node(startState, 0)
        return recursiveSearch(root, limit, startTime)
    }

    fun recursiveSearch(node: Node, limit: Int, startTime : Long): Result {
        iterations++
        memoryStateCounter -= 1
        maxMemoryStateCounter = max(maxMemoryStateCounter, memoryStateCounter)
        if (node.state.sameState(goal)) return Result(node, ResultType.SOLUTION)
        if (node.depth >= limit || !isEnoughMemory() || !isEnoughTime(startTime)) {
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