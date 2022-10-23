class Node(
    val state: State,
    val depth: Int,
    val parent: Node? = null,
    val action: Action? = null
) {
    override fun toString(): String {
        var nodeString = "State:\n$state"
        nodeString += "Action: $action" +
                "\nDepth: $depth"
        return nodeString
    }

    fun expand() : MutableList<Node> {
        val successors = mutableListOf<Node>()
        val actions = state.getPossibleActions()
        for (i in actions) {
            successors.add(Node(state.doAction(i), depth + 1, this, i))
        }
        return successors
    }

}