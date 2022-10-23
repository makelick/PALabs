import kotlin.random.Random.Default.nextInt

class Generator {
    fun generateState(): State {
        val matrix = List(3) { mutableListOf<Int>() }
        for (i in 0..8) {
            var row = nextInt(0, 3)
            while (matrix[row].size >= 3) {
                row = (row + 1) % 3
            }
            matrix[row].add(i)
        }
        matrix.forEach { it.shuffle() }
        return State(matrix)
    }
}