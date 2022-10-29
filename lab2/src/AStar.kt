import java.util.*

class AStar(val startState: State) : Algorithm() {

    fun search(startTime : Long): Result {
        val root = Node(startState, 0)
        val h: Comparator<Node> = compareBy { it.value }
        val queue = PriorityQueue(h)
        val closedList = mutableListOf<Node>()
        queue.add(root)
        while (queue.isNotEmpty()) {
            var isNewStates = false
            iterations++
            val currentNode = queue.remove()
            closedList.add(currentNode)
            if (currentNode.state.sameState(goal)) return Result(currentNode, ResultType.SOLUTION)
            if (!isEnoughMemory() || !isEnoughTime(startTime)) {
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
}