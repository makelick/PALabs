class LDFS(val startState: State) {
    private val goal = State(
        listOf(
            mutableListOf(0, 1, 2),
            mutableListOf(3, 4, 5),
            mutableListOf(6, 7, 8),
        )
    )

    fun search(limit: Int): Result {
        val root = Node(startState, 0)
        return recursiveSearch(root, limit)
    }

    fun recursiveSearch(node: Node, limit: Int): Result {
        if (node.state.sameState(goal)) return Result(node, ResultType.SOLUTION)
        if (node.depth >= limit) return Result(node, ResultType.CUTOFF)
        val successors = node.expand()
        for (i in successors) {
            val res = recursiveSearch(i, limit)
            if (res.type == ResultType.SOLUTION) return res
        }
        return Result(node, ResultType.FAIL)
    }
}