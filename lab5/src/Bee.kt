class Bee(private val graph : Graph) {
    var nodes = mutableSetOf<Node>()
    val fitness : Int
        get() = if (isClique(nodes)) nodes.size else 0

    fun generateRandomSolution() {
        nodes = graph.nodes.shuffled()
            .take((2..graph.maxDegree).random())
            .toMutableSet()
    }

    fun modifySolution(newNodes : MutableSet<Node>) {
        nodes = mutableSetOf()
        nodes.addAll(newNodes)
        var counter = 0
        do {
            var isModified = false
            val newNode = graph.nodes.shuffled().first()
            if (!nodes.contains(newNode)) {
                nodes.add(newNode)
                isModified = true
            }
            counter++
        } while (counter < nodes.size && !isModified)
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