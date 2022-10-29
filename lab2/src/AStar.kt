import java.util.*

class AStar(val startState: State) {
    private val goal = State(
        listOf(
            mutableListOf(0, 1, 2),
            mutableListOf(3, 4, 5),
            mutableListOf(6, 7, 8),
        )
    )
    var iterations = 0
    var totalStateCounter = 1
    var isNewStates = false
    var deadEndCounter = 0

    fun search(startTime : Long): Result {
        val root = Node(startState, 0)
        val h: Comparator<Node> = compareBy { it.value }
        val queue = PriorityQueue(h)
        val closedList = mutableListOf<Node>()
        queue.add(root)
        while (queue.isNotEmpty()) {
            isNewStates = false
            iterations++
            val currentNode = queue.remove()
            closedList.add(currentNode)
            if (currentNode.state.sameState(goal)) return Result(currentNode, ResultType.SOLUTION)
            if (!Statistic().isEnoughMemory() || !Statistic().isEnoughTime(startTime)) {
                deadEndCounter++
                return Result(currentNode, ResultType.CUTOFF)
            }
            val successors = currentNode.expand()
            for (i in successors) {
                var flag = true
                for (j in closedList) {
                    if (j.state.sameState(i.state)) {
                        flag = false
                        break
                    }
                }
                if (flag) {
                    queue.add(i)
                    totalStateCounter++
                    isNewStates = true
                }
            }
            if (!isNewStates) deadEndCounter++
        }
        return Result(root, ResultType.FAIL)
    }

    fun printStats() {
        println("Iterations: $iterations\n" +
                "Dead ends: $deadEndCounter\n" +
                "Total states: $totalStateCounter\n" +
                "Max states in memory: $totalStateCounter")
    }
}