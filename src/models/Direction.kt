package models

enum class Direction(val shit: Int) {
    RIGHT(-1),
    UP(-2),
    LEFT(-3),
    DOWN(-4);

    fun nextPosition(i: Int, j: Int): Pair<Int, Int> {
        return when (this) {
            RIGHT -> i to (j+1)
            UP -> (i-1) to j
            LEFT -> i to (j-1)
            DOWN -> (i+1) to j
        }
    }

    fun move90Degrees(): Direction = when(this) {
        RIGHT -> DOWN
        UP -> RIGHT
        LEFT -> UP
        DOWN -> LEFT
    }
}
