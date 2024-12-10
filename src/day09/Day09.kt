package day09

import println
import readInput
import kotlin.math.min

private const val FOLDER_NAME = "day09"
private const val FILE_NAME = "Day09"

private data class Shit(
    val size: Int,
    val isFreeSpace: Boolean,
    val originalIndex: Int
) {
    override fun toString(): String = buildString {
        repeat(size) {
            if (isFreeSpace) {
                append(".")
            } else {
                append("$originalIndex")
            }
        }
    }
}

fun main() {

    println('1'.digitToInt())

    fun part1(input: List<String>): Long {
        var isFreeSpace = false
        var shitIndex = 0
        val shits = mutableListOf<Shit>()
        val output = mutableListOf<Shit>()

        input.forEach { line ->
            line
                .toCharArray()
                .map { it.digitToInt() }
                .forEachIndexed { index, i ->
                    shits.add(Shit(
                        size = i,
                        isFreeSpace = isFreeSpace,
                        originalIndex = shitIndex
                    ))
                    if (!isFreeSpace) shitIndex++
                    isFreeSpace = !isFreeSpace
                }
        }
        println(shits)

        // 00...111...2...333 44 . 5555 . 6666 . 777 . 8888 99
        // 0  1  2  3 4 5 6 7 8  9  10  11 12 13  14 15 16  17
        var i = 0
        var j = shits.size - 1
        while (i < j) {
            if (!shits[i].isFreeSpace) {
                output.add(shits[i])
                i++
                continue
            } else if (shits[j].isFreeSpace) {
                j--
                continue
            } else {
                val minSize = min(shits[i].size, shits[j].size)
                output.add(Shit(
                    size = minSize,
                    isFreeSpace = false,
                    originalIndex = shits[j].originalIndex
                ))
                if (minSize == shits[i].size) i++
                else {
                    shits[i] = shits[i].copy(size = shits[i].size - minSize)
                }

                if (minSize == shits[j].size) j--
                else {
                    shits[j] = shits[j].copy(size = shits[j].size - minSize)
                }
            }
        }

        i = 0
        var sum = 0L
        output.forEach { shit ->
            repeat(shit.size) {
                sum += i++ * shit.originalIndex
            }
        }
        return sum
    }

    fun part2(input: List<String>): Long {
        var isFreeSpace = false
        var shitIndex = 0
        val shits = mutableListOf<Shit>()
        val output = mutableListOf<Shit>()

        input.forEach { line ->
            line
                .toCharArray()
                .map { it.digitToInt() }
                .forEachIndexed { index, i ->
                    shits.add(Shit(
                        size = i,
                        isFreeSpace = isFreeSpace,
                        originalIndex = shitIndex
                    ))
                    if (!isFreeSpace) shitIndex++
                    isFreeSpace = !isFreeSpace
                }
        }
        println(shits)

        var i = 0
        var j = shits.size - 1
        while (i < shits.size - 1) {
            if (!shits[i].isFreeSpace) {
                output.add(shits[i])
                i++
                continue
            }
            if (shits[i].size == 0) {
                i++
                continue
            }

            var resultFound = false
            j = shits.size - 1
            while (i < j) {
                if (shits[j].isFreeSpace) {
                    j--
                    continue
                } else {
                    if (shits[j].size <= shits[i].size) {
                        output.add(shits[j])
                        shits[i] = shits[i].copy(size = shits[i].size - shits[j].size)
                        shits[j] = shits[j].copy(isFreeSpace = true)
                        resultFound = true
                        break
                    } else {
                        j--
                    }
                }
            }

            if (!resultFound) {
                output.add(shits[i])
                i++
            }
        }

        i = 0
        var sum = 0L
        output.forEach { shit ->
            repeat(shit.size) {
//                println("$i * ${shit.originalIndex}")
                val originalIndex = if (shit.isFreeSpace) 0 else shit.originalIndex
                sum += i++ * originalIndex
            }
        }

//        println(output)

//        println("sum=$sum")
        return sum
    }

    val testInput = readInput(FOLDER_NAME, "${FILE_NAME}_test")
//    check(part1(testInput) == 1928L)
    check(part2(testInput) == 2858L)

    val input = readInput(FOLDER_NAME, FILE_NAME)
//    part1(input).println()
    part2(input).println()

}
