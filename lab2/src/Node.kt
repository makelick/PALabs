class Node(
    val state: State,
    val depth: Int,
    val parent: Node? = null,
    val action: Action? = null
)
{
    val value = state.countIncorrectPosition()
    override fun toString(): String {
        val nodeString = "Action: $action\nState:\n${state}Depth: $depth\n"
        return nodeString
    }

    fun expand(): MutableList<Node> {
        val successors = mutableListOf<Node>()
        val actions = state.getPossibleActions()
        action?.let { actions.remove(it.antiAction()) }
        for (i in actions) {
            successors.add(Node(state.doAction(i), depth + 1, this, i))
        }
        return successors
    }

    fun printSolution() {
        val way = mutableListOf<Node>()
        var parentNode: Node? = this
        while (parentNode != null) {
            way.add(parentNode)
            parentNode = parentNode.parent
        }
        for (i in way.lastIndex - 1 downTo 0) println(way[i])
    }
}