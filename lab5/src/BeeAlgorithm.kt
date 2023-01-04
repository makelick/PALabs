class BeeAlgorithm(graph: Graph) {
    val foragerBeesNumber = 55
    val scoutBeesNumber = 45
    val maxIterations = 1000
    val eliteBeesNumber = 10

    val scoutBees = (0 until scoutBeesNumber).map { Bee(graph) }.toMutableList()
    val foragerBees = (0 until foragerBeesNumber).map { Bee(graph) }.toMutableList()

    fun run() : Bee? {
        var bestSolution : Bee? = null
        for (iteration in 0..maxIterations) {
            scoutBees.forEach { it.generateRandomSolution() }

            val eliteBees = selectEliteBees()
            for (i in foragerBees.indices) {
                foragerBees[i].modifySolution(eliteBees[i % eliteBeesNumber].nodes)
            }
            replaceWorstSolutions(eliteBees)
            bestSolution = (foragerBees + scoutBees).maxBy { it.fitness }
            println("Iteration: $iteration, Solution: ${bestSolution.fitness}")
        }

        return bestSolution
    }

    fun selectEliteBees() : List<Bee> {
        return (foragerBees + scoutBees).sortedByDescending { it.fitness }.take(eliteBeesNumber)
    }

    fun replaceWorstSolutions(eliteBees : List<Bee>) {
        foragerBees.sortBy { it.fitness }
        for (i in 0 until eliteBeesNumber) {
            foragerBees[i] = eliteBees[i]
        }
    }
}