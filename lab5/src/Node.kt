class Node(val id : Int, private val adjacentNodes : MutableSet<Node> = mutableSetOf()) {
    fun addNeighbor(node : Node) {
        adjacentNodes.add(node)
    }

    fun areNeighbors(node : Node) : Boolean {
        return adjacentNodes.contains(node)
    }
}