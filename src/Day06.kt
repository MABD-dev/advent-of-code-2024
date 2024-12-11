import models.Direction


fun isOutside(input: List<List<Int>>, i: Int, j: Int): Boolean {
    return i < 0 || i >= input.size || j < 0 || j >= input[i].size
}

fun canReachLoop(input: List<List<Int>>, direction: Direction, i: Int, j: Int): Boolean {
    if (input[i][j] == direction.shit) return true

    val mutableInput = input.map { it.toMutableList() }.toMutableList()

    val (a, b) = direction.nextPosition(i, j)

    if (isOutside(input, a, b)) return false

    if (input[a][b] == 2) {
        return canReachLoop(mutableInput, direction.move90Degrees(), i, j)
    }

    mutableInput[a][b] = direction.shit
    return canReachLoop(mutableInput, direction, a, b)

}

fun exitMaze(input: MutableList<MutableList<Int>>, direction: Direction, i: Int, j: Int): Int {
    input[i][j] = direction.shit

    val (a, b) = direction.nextPosition(i, j)
    if (isOutside(input, a, b)) {
        return input.sumOf { it.count { x -> x < 0 } }
    }

    if (input[a][b] == 2) {
        return exitMaze(input, direction.move90Degrees(), i, j)
    }

    return exitMaze(input, direction, a, b)

}

fun main() {

    fun part1(input: List<String>): Int {
        var i = 0
        var j = 0
        var direction = Direction.UP

        val a = input.mapIndexed { lineInde, line ->
            line.trim()
                .toCharArray()
                .mapIndexed { columnIndex, char ->
                    when (char) {
                        '.' -> 0
                        '#' -> 2
                        '^' -> {
                            i = lineInde
                            j = columnIndex
                            0
                        }
                        else -> 0

                    }
                }.toMutableList()
        }.toMutableList()

        return exitMaze(a, direction, i, j)
            .also { println("result = $it") }


//        while (true) {
//            val (newi, newj) = direction.nextPosition(i, j)
//            if (newi < 0 || newi >= a.size || newj < 0 || newj >= a[newi].size ) {
//                a[i][j] = 1
//                break
//            }
//
//            val nextPositionValue = a[newi][newj]
//            when (nextPositionValue) {
//                2 -> {
//                    direction = direction.move90Degrees()
//                }
//                else -> {
//                    a[i][j] = 1
//                    i = newi
//                    j = newj
//                }
//            }
//
//        }
//
//        return a.sumOf { it.count{ x -> x == 1 } }

    }

    fun part2(input: List<String>): Int {
        var i = 0
        var j = 0
        var direction = Direction.UP
        var loopCounter = 0

        val a = input.mapIndexed { lineInde, line ->
            line.trim()
                .toCharArray()
                .mapIndexed { columnIndex, char ->
                    when (char) {
                        '.' -> 0
                        '#' -> 2
                        '^' -> {
                            i = lineInde
                            j = columnIndex
                            0
                        }
                        else -> 0

                    }
                }.toMutableList()
        }.toMutableList()

        var switched = false

        while (true) {
            val (newi, newj) = direction.nextPosition(i, j)
            if (isOutside(a, newi, newj)) {
                a[i][j] = 1
                break
            }

            when (a[newi][newj]) {
                2 -> {
                    direction = direction.move90Degrees()
                    switched = true
                }
                else -> {
                    if (!switched && canReachLoop(a, direction, i, j)) {
                        loopCounter++
                    }

                    a[i][j] = direction.shit
                    i = newi
                    j = newj
                    switched = false
                }
            }
        }
        return loopCounter
    }

    val testInput = readTestInput()
    check(part1(testInput) == 41)
//    check(part2(testInput) == 6)

    val input = readInput()
    part1(input).println()
//    part2(input).println()


}
