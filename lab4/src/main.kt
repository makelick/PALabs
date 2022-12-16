fun main() {
    val numberOfItems = 100
    val populationSize = 100
    val backpackCapacity = 250

    val items = mutableListOf<Item>()
    for (i in 0..numberOfItems) {
        items.add(Item())
    }

    val population = Population(items, populationSize)
    population.createPopulation()

    GeneticAlgorithm(population, backpackCapacity).run()
}