package day01

import println
import readInput
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Long {
        val (list1, list2) = input.map { line ->
            val first = line.substringBefore(" ").toLong()
            val second = line.substringAfterLast(" ").toLong()
            first to second
        }.unzip()

        return list1.sorted().zip(list2.sorted())
            .sumOf { (a, b) -> abs(a - b) }
    }

    fun part2(input: List<String>): Long {
        val (list1, list2) = input.map { line ->
            val first = line.substringBefore(" ").toLong()
            val second = line.substringAfterLast(" ").toLong()
            first to second
        }.unzip()

        val list2NumberToCountMap = list2.groupingBy { it }.eachCount()
        return list1.sumOf {
            it * list2NumberToCountMap.getOrDefault(it, 0)
        }
    }

    // Test if implementation meets criteria from the description, like:

    // Or read a large test input from the `src/Day02_test.txt` file:
    val testInput = readInput("day01", "Day01_test")
    check(part1(testInput) == 11L)
    check(part2(testInput) == 31L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("day01", "Day01")
    part1(input).println()
    part2(input).println()

}
