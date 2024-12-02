package day01

import println
import readInput
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val list1 = mutableListOf<Int>()
        val list2 = mutableListOf<Int>()

        input
            .forEach {
                if (it.isBlank()) return@forEach
                val (a, b) = it.split("   ")
                list1.add(a.toInt())
                list2.add(b.toInt())
            }
        list1.sort()
        list2.sort()

        return list1.zip(list2)
            .sumOf { (a, b) ->
                abs(a - b)
            }
    }

    fun part2(input: List<String>): Int {
        val list1 = mutableListOf<Int>()
        val list2 = mutableListOf<Int>()
        val list2NumberToCountMap = mutableMapOf<Int, Int>()
        input
            .forEach {
                if (it.isBlank()) return@forEach
                val (a, b) = it.split("   ")
                val intB = b.toInt()
                list1.add(a.toInt())
                list2.add(intB)

                list2NumberToCountMap[intB] = list2NumberToCountMap.getOrDefault(intB, 0) + 1
            }

        return list1.sumOf {
            it * list2NumberToCountMap.getOrDefault(it, 0)
        }
    }

    // Test if implementation meets criteria from the description, like:

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("day01", "Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("day01", "Day01")
    part1(input).println()
    part2(input).println()

}
