fun main() {
    val regex = """mul\((?<x>\d{1,3}),(?<y>\d{1,3})\)""".toRegex()
    val combinedRegex = """(mul\((?<x>\d{1,3}),(?<y>\d{1,3})\))|(do\(\)|don't\(\))""".toRegex()


    fun part1(input: List<String>): Long {
        return input.map { line ->
            val matches = regex.findAll(line)
            matches.sumOf { match ->
                val x = match.groups["x"]?.value?.toLong() ?: 0
                val y = match.groups["y"]?.value?.toLong() ?: 0
                x * y
            }
        }.sum()
    }

    fun part2(input: List<String>): Long {
        return input.sumOf { line ->
            var calculate = true
            var result = 0L

            combinedRegex.findAll(line).forEach { match ->
                when {
                    match.value.startsWith("don") -> calculate = false
                    match.value.startsWith("do") -> calculate = true
                    else -> {
                        if (calculate) {
                            val x = match.groups["x"]?.value?.toLong() ?: 0
                            val y = match.groups["y"]?.value?.toLong() ?: 0
                            result += x * y
                        }
                    }
                }
            }
            result
        }.also { println("shit = $it") }
    }

    val testInput = readTestInput()
    check(part1(testInput) == 161L)
    check(part2(testInput) == 48L)

    val input = readInput()
    part1(input).println()
    part2(input).println()


}
