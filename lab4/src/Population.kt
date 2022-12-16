class Population {
    val individuals: MutableList<Individual> = mutableListOf()

    fun createPopulation() {
        for (i in 0..100) {
            individuals.add(Individual(MutableList(100) {false}).also { it.chromosome[i] = true })
        }
    }

    fun add(individual: Individual) {
        individuals.add(individual)
    }

    fun getBest(): Individual {
        individuals.sortBy { it.weight }
        return individuals[0]
    }
}