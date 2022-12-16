class Individual(private val items : MutableList<Item>) {
    val chromosome : MutableList<Boolean> = MutableList(items.size) {false}
    val weight: Int
        get() {
            var sum = 0
            for (i in 0..items.size) {
                if (chromosome[i]) {
                    sum += items[i].weight
                }
            }
            return sum
        }

    fun crossover(other: Individual): Individual {
        val child = Individual(items)
        child.chromosome.clear()
        child.chromosome.addAll(chromosome.subList(0, items.size/4))
        child.chromosome.addAll(other.chromosome.subList(items.size/4, items.size/2))
        child.chromosome.addAll(chromosome.subList(items.size/2, items.size/4*3))
        child.chromosome.addAll(other.chromosome.subList(items.size/4*3, items.size))
        return child
    }

    fun mutate() {
        if (Math.random() < 0.05) {
            val gen1 = (0..items.size).random()
            var gen2 = (0..items.size).random()
            while (gen1 == gen2) {
                gen2 = (0..items.size).random()
            }
            chromosome[gen1] = chromosome[gen2].also { chromosome[gen2] = chromosome[gen1] }
        }
    }
}