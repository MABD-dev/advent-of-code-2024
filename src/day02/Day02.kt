package day02

import println
import readInput
import kotlin.math.abs
import kotlin.math.min

private fun Boolean.toInt() = if (this) 1 else 0

private fun List<Long>.isIncreasing(): Int {
    var removalsCount = 0

    var i = 0
    var j = 1
    while (j < this.size) {
        if (this[i] > this[j]) {
            removalsCount++
        } else {
            i = j
        }
        j++
    }
    return removalsCount

}

private fun List<Long>.isDecreasing(): Int {
    var removalsCount = 0

    var i = 0
    var j = 1
    while (j < this.size) {
        if (this[i] < this[j]) {
            removalsCount++
        } else {
            i = j
        }
        j++
    }
    return removalsCount
}

private fun List<Long>.safeRanges(): Int {
    var removalsCount = 0
    var i = 0
    var j = 1

    while (j < this.size) {
        if (abs(this[i] - this[j]) !in 1..3) {
            removalsCount++
        } else {
            i = j
        }
        j++
    }
    return removalsCount
}

private fun List<Long>.isSafeReport(maxAllowedSkips: Int): Boolean {
    val a = this.isIncreasing()
    val b = this.isDecreasing()
    val c = this.safeRanges()

    if (a > maxAllowedSkips && b > maxAllowedSkips) return false
    if (c > maxAllowedSkips) return false

//    println("a=$a, b=$b, c=$c, list=$this")

    val count = a + b + c
    val isIncreasing = a == 0
    val isDecreasing = b == 0
    val isSafeRange = c == 0

    val shit = min(a, b) + c

    return shit <= maxAllowedSkips

    return if (isIncreasing || a == maxAllowedSkips) {
        (isSafeRange && (a+c) <= maxAllowedSkips)
    } else if (isDecreasing || b == maxAllowedSkips) {
        (isSafeRange && (b+c) <= maxAllowedSkips)
    } else {
        false
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.map { line ->
            val numbers = line.split(" ").map { it.toLong() }
            return@map numbers.isSafeReport(0).toInt()
        }.sum()
            .also { println("result(1) = $it") }
    }

    fun part2(input: List<String>): Int {
        return input.map { line ->
            val numbers = line.split(" ").map { it.toLong() }
            return@map numbers.isSafeReport(1).toInt()
        }.sum()
            .also { println("result(2)=$it") }
    }

    // Test if implementation meets criteria from the description, like:

    // Or read a large test input from the `src/Day02_test.txt` file:
    val testInput = readInput("day02", "Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)
//    check(part2(testInput) == 31L)
//
//    // Read the input from the `src/Day02.txt` file.
    val input = readInput("day02", "Day02")
    check(part1(input).also { println("part1=$it") } == 486) {
        println("should be 486")
    }
//    part1(input).println()
    part2(input).println()

}
