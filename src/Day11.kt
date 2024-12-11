
fun String.toIntWithoutZeros(): Int = this.toInt()

private fun calculateShit(
    stones: MutableList<Long>,
) {
    var i = 0
    while (i < stones.size) {
        if (stones[i] == 0L) {
            stones[i++] = 1L
        } else if (stones[i].toString().length % 2 == 0) {
            val str = stones[i].toString()
            val left = str.substring(0, str.length/2).toLong()
            val right = str.substring(str.length/2).toLong()
            stones.add(i, left)
            stones[++i] = right
            i++
        } else {
            stones[i++] *= 2024L
        }
    }
}

private fun seeHowMany(numbers: MutableList<Long>, steps: Int, cache: MutableMap<Long, MutableList<Long>>): Long {
//    if (cache.containsKey(number) && cache[number]!!.size > steps) {
//        println("cache hit, number=$number, steps=$steps")
//        return cache[number]!![steps]
//    }

    var result = 0L

//    repeat(steps) { step ->
//
//        // check if any of the numbers is already cached, if yes, remove it from numbers list + add size to result
//        for (i in numbers.indices) {
//            if (cache.containsKey(numbers[i] && ))
//        }
//
//
//        var i = 0
//        while (i < numbers.size) {
//            if (numbers[i] == 0L) {
//                numbers[i++] = 1L
//            } else if (numbers[i].toString().length % 2 == 0) {
//                val str = numbers[i].toString()
//                val left = str.substring(0, str.length/2).toLong()
//                val right = str.substring(str.length/2).toLong()
//                numbers.add(i, left)
//                numbers[++i] = right
//                i++
//            } else {
//                numbers[i++] *= 2024L
//            }
//        }
//        cache[numbers]!!.add(numbers.size.toLong())
//    }
    return result
}

private fun solve(
    stones: MutableList<Long>,
    steps: Int
): Long {

//    val cache = mutableMapOf<Long, MutableList<Long>>()
//    seeHowMany(0, steps, cache)
//    seeHowMany(1, steps, cache)
//    seeHowMany(2024, steps, cache)
//
//    var stoneIndex = 0
//    while (stoneIndex < stones.size) {
//        val number = stones[stoneIndex]
//        seeHowMany(number, steps, cache)
//
//        stoneIndex++
//    }
//
//    cache.keys.forEach { key ->
//        println("$key -> ${cache[key]}")
//    }

    return 0L
}

fun main() {

    fun part1(input: List<String>): Long {
        val stones = input[0].split(" ").map { it.toLong() }.toMutableList()
        return solve(stones, 25)
    }

    fun part2(input: List<String>): Long {
        val stones = input[0].split(" ").map { it.toLong() }.toMutableList()
        return solve(stones, 75)
    }

    val testInput = readTestInput()
    part1(testInput)
//    part2(testInput)
//    check(part1(testInput) == 55312L)
//    check(part2(testInput) == 0L)

    val input = readInput()
//    part1(input).println()
//    part2(input).println()


}
