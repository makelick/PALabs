import java.util.*

class AStar(val startState: State) {
    private val goal = State(
        listOf(
            mutableListOf(0, 1, 2),
            mutableListOf(3, 4, 5),
            mutableListOf(6, 7, 8),
        )
    )

    fun search(): Result {
        val root = Node(startState, 0)
        val h: Comparator<Node> = compareBy { it.state.countIncorrectPosition() }
        val queue = PriorityQueue(h)
        val closedList = mutableListOf<Node>()
        queue.add(root)
        while (queue.isNotEmpty()) {
            val currentNode = queue.remove()
            closedList.add(currentNode)
            println(closedList.size)
            if (currentNode.state.sameState(goal)) return Result(currentNode, ResultType.SOLUTION)
            val successors = currentNode.expand()
            for (i in successors) {
                var flag = true
                for (j in closedList) {
                    if (j.state.sameState(i.state)) {
                        flag = false
                        break
                    }
                }
                if (flag) queue.add(i)
            }
        }
        return Result(root, ResultType.FAIL)
    }
}