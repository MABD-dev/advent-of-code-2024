package day02

import println
import readInput
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.min

private fun areSafeLevels2(levels: List<Int>): Boolean {
    val zipped = levels.zipWithNext()
    val increasing = zipped.all { (a, b) -> a < b }
    val decreasing = zipped.all { (a, b) -> a> b }
    val safeDistance = zipped.all { (a, b) -> (a - b).absoluteValue in 1..3 }
    return (increasing || decreasing) && safeDistance
}

private fun areSafeLevels(levels: List<Int>): Boolean {
    var increasing = true
    var decreasing = true
    var safeDistance = true

    for (i in 0 until levels.size - 1) {
        safeDistance = safeDistance && (levels[i] - levels[i+1]).absoluteValue in 1..3
        when  {
            levels[i] < levels[i+1] -> decreasing = false
            levels[i] > levels[i+1] -> increasing = false
            else -> {
                decreasing = false
                increasing = false
            }
        }
    }

    return (increasing || decreasing) && safeDistance
}

fun main() {
    fun part1(input: List<String>): Int {
        val reports = input.map { line ->
            line.split(" ").map { it.toInt() }
        }
        return reports.count(::areSafeLevels)
    }

    fun part2(input: List<String>): Int {
        return input.count { line ->
            val levels = line.split(" ").map { it.toInt() }
            var safe = false
            for (i in levels.indices) {
                safe = areSafeLevels(levels.toMutableList().apply { removeAt(i) })
                if (safe) {
                    break
                }
            }
            safe
        }
    }

    val testInput = readInput("day02", "Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("day02", "Day02")
    part1(input).println()
    part2(input).println()

}
