package day05

import println
import readInput

fun main() {

    fun part1(input: List<String>): Int {
        var readingPairs = true
        val shitNumbers = mutableListOf<Pair<Int, Int>>()
        val updates = mutableListOf<List<Int>>()
        input.forEach { line ->
            if (line.isBlank()) {
                readingPairs = false
                return@forEach
            }

            if (readingPairs) {
                val (a, b) = line.split("|").map { it.toInt() }
                shitNumbers.add(a to b)
            } else {
                // read shit
                updates.add(line.split(",").map { it.toInt() })
            }

        }

        val numberToAllowedNumbers = shitNumbers.groupBy { it.first }
            .map { it.key to it.value.map { x -> x.second } }
            .toMap()

        var sum = 0
        updates.forEach { update ->
            var result = false
            var fixedUpdate = update.toMutableList()
            for (i in fixedUpdate.indices) {
                for (j in i+1 until fixedUpdate.size) {

                    if (!numberToAllowedNumbers.contains(fixedUpdate[i])) {
                        fixedUpdate.apply {
                            val temp = this[j]
                            this[j] = this[i]
                            this[i] = temp
                        }
                        result = true
                    } else {
                        if (numberToAllowedNumbers[fixedUpdate[i]]?.contains(fixedUpdate[j]) == false) {
                            fixedUpdate.apply {
                                val temp = this[j]
                                this[j] = this[i]
                                this[i] = temp
                            }
                            result = true
//                        break
                        }
                    }
                }
            }
            if (result) {
                sum += fixedUpdate[fixedUpdate.size/2].also { println("update=$fixedUpdate, adding=${it}") }
            }
        }

        return sum.also { println("sum = $it") }
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    // Test if implementation meets criteria from the description, like:

    // Or read a large test input from the `src/Day02_test.txt` file:
    val testInput = readInput("day05", "Day05_test")
    check(part1(testInput) == 123)
//    check(part2(testInput) == 9)

    // Read the input from the `src/Day05.txt` file.
    val input = readInput("day05", "Day05")
    part1(input).println()
//    part2(input).println()


}
