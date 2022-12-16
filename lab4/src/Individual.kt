class Individual(val chromosome : MutableList<Boolean> = MutableList(100) {false}) {
    val weight: Int
        get() {
            var sum = 0
            for (i in 0..100) {
                if (chromosome[i]) {
                    sum += items[i].weight
                }
            }
            return sum
        }
}