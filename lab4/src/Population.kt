class Population(val items : MutableList<Item>, val populationSize : Int) {
    val individuals: MutableList<Individual> = mutableListOf()

    fun createPopulation() {
        for (i in 0..populationSize) {
            individuals.add(Individual(items).also { it.chromosome[i] = true })
        }
    }

    fun add(individual: Individual) {
        individuals.add(individual)
    }

    fun getBest(): Individual {
        individuals.sortBy { it.weight }
        return individuals.first()
    }

    fun getWorst(): Individual {
        individuals.sortBy { it.weight }
        return individuals.last()
    }
}