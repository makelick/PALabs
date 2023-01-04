class Bee(private val graph : Graph) {
    var nodes = mutableSetOf<Node>()
    val fitness : Int
        get() = if (isClique(nodes)) nodes.size else 0

    fun generateRandomSolution() {
        nodes = graph.nodes.shuffled()
            .take((2..graph.nodes.size).random())
            .toMutableSet()
    }

    fun modifySolution(nodes : MutableSet<Node>) {
        do {
            var isAdded = false
            val newNode = graph.nodes.shuffled().first()
            if (!nodes.contains(newNode)) {
                nodes.add(newNode)
                isAdded = true
            }
        } while (!isAdded)
    }

    private fun isClique(nodes : MutableSet<Node>) : Boolean {
        for (node in nodes) {
            for (otherNode in nodes) {
                if (node != otherNode && !node.areNeighbors(otherNode)) {
                    return false
                }
            }
        }
        return true
    }
}