import kotlin.random.Random.Default.nextInt

class Generator {
    fun generateState(): State {
        var matrix: List<MutableList<Int>>
        do {
            matrix = List(3) { mutableListOf() }
            for (i in 0..8) {
                var row = nextInt(0, 3)
                while (matrix[row].size >= 3) {
                    row = (row + 1) % 3
                }
                matrix[row].add(i)
            }
            matrix.forEach { it.shuffle() }
        } while (!isSolvable(matrix))

        return State(matrix)
    }

    private fun isSolvable(matrix: List<MutableList<Int>>): Boolean {
        var inv = 0
        val linearForm = mutableListOf<Int>()
        for (row in matrix) linearForm.addAll(row)
        for (i in linearForm.indices) {
            for (j in i + 1..linearForm.lastIndex) {
                if (linearForm[j] > linearForm[i] && linearForm[i] != 0 && linearForm[j] != 0) inv++
            }
        }
        return inv % 2 == 0
    }
}