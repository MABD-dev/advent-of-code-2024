
private fun formatInput(input: List<String>): List<Long> {
    return input[0].split(" ").map { it.toLong() }
}

private fun blink(
    stones: MutableList<Long>,
    steps: Int
): Long {
    var map = mutableMapOf<Long, Long>()
    stones.forEach { num -> map[num] = map.getOrDefault(num, 0L) + 1 }

    repeat(steps) {
        val newMap = mutableMapOf<Long, Long>()

        map.forEach { (number, oldCount) ->
            if (number == 0L) {
                newMap[1L] = newMap.getOrDefault(1L, 0L) + oldCount
            } else if (number.toString().length % 2 == 0) {
                val numberStr = number.toString()
                val leftValue = numberStr.substring(0, numberStr.length/2).toLong()
                val rightValue = numberStr.substring(numberStr.length/2).toLong()

                newMap[leftValue] = newMap.getOrDefault(leftValue, 0L) + oldCount
                newMap[rightValue] = newMap.getOrDefault(rightValue, 0L) + oldCount
            } else {
                val newNumber = number * 2024
                newMap[newNumber] = newMap.getOrDefault(newNumber, 0L) + oldCount
            }
            map = newMap
        }
    }
    return map.values.sum()
}

fun main() {

    fun part1(input: List<String>): Long {
        val stones = formatInput(input).toMutableList()
        return blink(stones, 25)
    }

    fun part2(input: List<String>): Long {
        val stones = formatInput(input).toMutableList()
        return blink(stones, 75)
    }

    val testInput = readTestInput()
//    check(part1(testInput) == 55312L)
//    check(part2(testInput) == 0L)

    val input = readInput()
    part1(input).println() // 204022
    part2(input).println() // 241651071960597


}
