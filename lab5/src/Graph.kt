class Graph(nodesNum : Int, maxDegree : Int) {
    private val nodes : Set<Node> =
        (0 until nodesNum).map { Node(it) }.toSet()

    init {
        nodes.forEach { node ->
            val degree = (0..maxDegree).random()
            val neighbors = nodes.filter{ it != node }
                .shuffled()
                .take(degree)
            neighbors.forEach { node.addNeighbor(it) }
        }
    }
}