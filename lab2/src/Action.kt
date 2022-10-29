enum class Action(val row: Int, val col: Int) {
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1);

    fun antiAction() : Action {
        return if (this == UP) DOWN
            else if (this == DOWN) UP
            else if (this == LEFT) RIGHT
            else LEFT
    }
}