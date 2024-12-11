fun main() {

    fun searchX(input: List<List<Char>>, i: Int, j: Int, x: String, k: Int, addI: Int, addJ: Int): Int {
        if (k >= x.length) return 1
        if (i >= input.size || j >= input[i].size || j < 0) return 0

        if (input[i][j] == x[k]) {
            return searchX(input, i + addI, j + addJ, x, k + 1, addI, addJ)
        }
        return 0

    }

    fun searchPart1(input: List<List<Char>>, x: String): Int {
        var sum = 0
        for (i in input.indices) {
            for (j in input[i].indices) {
                sum += searchX(input, i, j, x, 0, 0, 1) // horizontally
                sum += searchX(input, i, j, x, 0, 1, 0) // vertically
                sum += searchX(input, i, j, x, 0, 1, 1) // diagonally to bottom-right
                sum += searchX(input, i, j, x, 0, 1, -1) // diagonally to bottom-left
            }
        }
        return sum
    }

    fun searchForArray(input: List<List<Char>>, i: Int, j: Int, x: List<List<Char>>): Int {
        for (a in x.indices) {
            val newI = i + a
            if (newI >= input.size) return 0

            for (b in x[a].indices) {
                val newJ = j + b
                if (newJ >= input[newI].size) return 0

                if (x[a][b] != '.' && input[newI][newJ] != x[a][b]) return 0
            }

        }
        return 1
    }

    fun part1(input: List<String>): Int {
        val a = input.map { it.toCharArray().toList() }
        val x1 = "XMAS"
        val x2 = "SAMX"

        return searchPart1(a, x1) + searchPart1(a, x2)
    }

    fun part2(input: List<String>): Int {
        val a = input.map { it.toCharArray().toList() }
        val x1 = listOf(
            listOf('M', '.', 'S'),
            listOf('.', 'A', '.'),
            listOf('M', '.', 'S')
        )
        val x2 = listOf(
            listOf('S', '.', 'S'),
            listOf('.', 'A', '.'),
            listOf('M', '.', 'M')
        )
        val x3 = listOf(
            listOf('M', '.', 'M'),
            listOf('.', 'A', '.'),
            listOf('S', '.', 'S')
        )
        val x4 = listOf(
            listOf('S', '.', 'M'),
            listOf('.', 'A', '.'),
            listOf('S', '.', 'M')
        )


        var sum = 0
        for (i in input.indices) {
            for (j in input[i].indices) {
                sum += searchForArray(a, i, j, x1)
                sum += searchForArray(a, i, j, x2)
                sum += searchForArray(a, i, j, x3)
                sum += searchForArray(a, i, j, x4)
            }
        }
        return sum
    }

    val testInput = readTestInput()
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    val input = readInput()
    part1(input).println()
    part2(input).println()


}
