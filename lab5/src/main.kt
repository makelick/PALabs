fun main() {
    val nodesNumber = 300
    val maxDegree = 150

    val graph = Graph(nodesNumber, maxDegree)

    val solution = BeeAlgorithm(graph).run()

    println("Max clique size: ${solution?.fitness}\n" +
            "Max clique: ${solution?.nodes}")
}