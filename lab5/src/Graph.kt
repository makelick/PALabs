class Graph(nodesNum : Int, maxDegree : Int) {
    val nodes : Set<Node> =
        (0 until nodesNum).map { Node(it) }.toSet()

    init {
        nodes.forEach { node ->
            val degree = (2..maxDegree).random()
            val neighbors = nodes.filter{ it != node }
                .shuffled()
                .take(degree)
            neighbors.forEach { node.addNeighbor(it) }
        }
    }
}