class GeneticAlgorithm(private val population: Population, private val backpackCapacity: Int) {
    fun run() {
        for (i in 0..1000) {
            val parent1 = population.getBest()
            var parent2 = population.individuals[(0..population.populationSize).random()]
            while (parent1 == parent2) {
                parent2 = population.individuals[(0..population.populationSize).random()]
            }
            val child = parent1.crossover(parent2)
            child.mutate()
            child.localImprovement()
            if (child.weight <= backpackCapacity) {
                population.add(child)
                population.individuals.remove(population.getWorst())
            }
        }
        println(population.getBest())
    }
}