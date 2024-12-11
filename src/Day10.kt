import models.Position

private fun Position.moveRight() = this.copy(x = x, y = y + 1)
private fun Position.moveLeft() = this.copy(x = x, y = y - 1)
private fun Position.moveUp() = this.copy(x = x - 1, y = y)
private fun Position.moveDown() = this.copy(x = x + 1, y = y)

private fun Position.isValid(width: Int, height: Int): Boolean {
    return this.x in 0..<width && this.y in 0..< height
}

private inline fun Position.ifValid(width: Int, height: Int, block: (Position) -> Unit) {
    if (isValid(width, height)) block(this)
}

fun main() {

    fun part1(input: List<String>): Long {
        fun searchFor9UniquePositions(
            input: List<List<Int>>,
            i: Int,
            j: Int,
            currentNumber: Int,
            result: MutableSet<Position>
        ) {
            if (i < 0 || i >= input.size || j < 0 || j >= input[i].size) {
                return
            }
            if (input[i][j] == 9) {
                result.add(Position(i, j))
                return
            }

            // check right
            if (j + 1 < input[i].size && input[i][j+1] == currentNumber + 1) {
                searchFor9UniquePositions(input, i, j + 1, currentNumber + 1, result)
            }

            // check left
            if (j - 1 >= 0 && input[i][j-1] == currentNumber + 1) {
                searchFor9UniquePositions(input, i, j - 1, currentNumber + 1, result)
            }

            // check bottom
            if (i + 1 < input.size && input[i+1][j] == currentNumber + 1) {
                searchFor9UniquePositions(input, i + 1, j, currentNumber + 1, result)
            }

            // check top
            if (i - 1 >= 0 && input[i-1][j] == currentNumber + 1) {
                searchFor9UniquePositions(input, i - 1, j, currentNumber + 1, result)
            }
        }

        val intInput = input.map { line ->
            line.toCharArray().map {
                it.toString().toIntOrNull() ?: -1
            }
        }
        var result = 0L
        val positionsSet = mutableSetOf<Position>()

        for (i in intInput.indices) {
            for (j in 0 until intInput[i].size) {
                if (intInput[i][j] == 0) {
                    positionsSet.clear()
//                    println("searching from i=$i, j=$j")
                    searchFor9UniquePositions(intInput, i, j, 0, positionsSet)
//                    println("positionsSet = $positionsSet\n*********************")
                    result += positionsSet.size
                }
            }
        }
        return result
    }

    fun part2(input: List<String>): Long {
        fun searchFor9UniquePositions(
            input: List<List<Int>>,
            i: Int,
            j: Int,
            currentNumber: Int,
            result: MutableList<Position>
        ) {
            if (i < 0 || i >= input.size || j < 0 || j >= input[i].size) {
                return
            }
            if (input[i][j] == 9) {
                result.add(Position(i, j))
                return
            }

            // check right
            if (j + 1 < input[i].size && input[i][j+1] == currentNumber + 1) {
                searchFor9UniquePositions(input, i, j + 1, currentNumber + 1, result)
            }

            // check left
            if (j - 1 >= 0 && input[i][j-1] == currentNumber + 1) {
                searchFor9UniquePositions(input, i, j - 1, currentNumber + 1, result)
            }

            // check bottom
            if (i + 1 < input.size && input[i+1][j] == currentNumber + 1) {
                searchFor9UniquePositions(input, i + 1, j, currentNumber + 1, result)
            }

            // check top
            if (i - 1 >= 0 && input[i-1][j] == currentNumber + 1) {
                searchFor9UniquePositions(input, i - 1, j, currentNumber + 1, result)
            }
        }

        val intInput = input.map { line ->
            line.toCharArray().map {
                it.toString().toIntOrNull() ?: -1
            }
        }
        var result = 0L
        val positionsSet = mutableListOf<Position>()

        for (i in intInput.indices) {
            for (j in 0 until intInput[i].size) {
                if (intInput[i][j] == 0) {
                    positionsSet.clear()
                    searchFor9UniquePositions(intInput, i, j, 0, positionsSet)
                    result += positionsSet.size
                }
            }
        }
        return result
    }

    val testInput = readTestInput()
//    check(part1(testInput) == 36L)
    check(part2(testInput) == 81L)

    val input = readInput()
//    part1(input).println()
    part2(input).println()

}
