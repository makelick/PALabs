class State(private val matrix: List<MutableList<Int>>) {
    fun getPossibleActions(): MutableList<Action> {
        val actions = mutableListOf<Action>()
        val pos = getPointerPosition()
        if (pos.first != 0) actions.add(Action.UP)
        if (pos.second != matrix[0].lastIndex) actions.add(Action.RIGHT)
        if (pos.first != matrix.lastIndex) actions.add(Action.DOWN)
        if (pos.second != 0) actions.add(Action.LEFT)
        return actions
    }

    fun getPointerPosition(): Pair<Int, Int> {
        for (row in matrix.indices) {
            for (col in matrix[row].indices) {
                if (matrix[row][col] == 0) return Pair(row, col)
            }
        }
        throw Exception("Current state haven`t empty element.")
    }

    fun doAction(action: Action): State {
        val newMatrix = List(matrix.size) { i -> matrix[i].toMutableList() }
        val ptr = getPointerPosition()
        newMatrix[ptr.first][ptr.second] = newMatrix[ptr.first + action.row][ptr.second + action.col].also {
            newMatrix[ptr.first + action.row][ptr.second + action.col] = newMatrix[ptr.first][ptr.second]
        }
        return State(newMatrix)
    }

    override fun toString(): String {
        var resString = ""
        matrix.forEach { resString += it.joinToString(prefix = "\t", separator = " ", postfix = "\n") }
        return resString
    }

    fun sameState(other: State): Boolean {
        for (row in matrix.indices) {
            for (col in matrix[row].indices) {
                if (matrix[row][col] != other.matrix[row][col]) return false
            }
        }
        return true
    }

    fun countIncorrectPosition() : Int {
        var counter = 0
        for (i in 0..8) {
            if (matrix[i/3][i % 3] != i && matrix[i/3][i % 3] != 0) counter++
        }
        return counter
    }
}