import models.Position

/**
 * assuming this position is the midpoint between [pos2] and the hashtag
 */
private fun Position.calculateHashtag(pos2: Position): Position {
    return Position(
        r = 2 * this.r - pos2.r,
        c = 2 * this.c - pos2.c
    )
}

private fun Position.isInBounds(width: Int, height: Int): Boolean = this.r in 0..<width && this.c in 0..<height

fun main() {

    fun part1(input: List<String>): Int {
        fun findAntennasCount(
            input: Map<Char, List<Position>>,
            width: Int,
            height: Int
        ): Int {
            val foundedPositions = mutableSetOf<Position>()
            return input.keys.sumOf { char ->
                var result = 0
                val positions = input[char]!!

                for (i in positions.indices) {
                    for (j in i + 1 until positions.size) {
                        val pos1 = positions[i]
                        val pos2 = positions[j]

                        val hashtag1Position = pos1.calculateHashtag(pos2)
                        if (hashtag1Position.isInBounds(width, height) && foundedPositions.add(hashtag1Position)) {
                            result++
                        }

                        val hashtag2Position = pos2.calculateHashtag(pos1)
                        if (hashtag2Position.isInBounds(width, height) && foundedPositions.add(hashtag2Position)) {
                            result++
                        }
                    }
                }
                result
            }
        }

        val charsMap = mutableMapOf<Char, MutableList<Position>>()
        val height = input.size
        val width = input[0].length

        input.forEachIndexed { i, line ->
            line.toCharArray().forEachIndexed charLoop@ { j, char ->
                if (char == '.' || char == '#') return@charLoop
                charsMap[char] = charsMap.getOrDefault(char, mutableListOf()).apply { add(Position(i, j)) }
            }
        }
        return findAntennasCount(charsMap, width, height)
    }

    fun part2(input: List<String>): Int {
        val charsMap = mutableMapOf<Char, MutableList<Position>>()
        val foundedPositions = mutableSetOf<Position>()

        val height = input.size
        val width = input[0].length
        input.forEachIndexed { i, line ->
            line.toCharArray().forEachIndexed charLoop@ { j, char ->
                if (char == '.' || char == '#') return@charLoop
                charsMap[char] = charsMap.getOrDefault(char, mutableListOf()).apply { add(Position(i, j)) }
            }
        }

        fun findAllPossiblePositions(
            _pos1: Position,
            _pos2: Position
        ): Int {
            var pos1 = _pos1
            var pos2 = _pos2
            var result = 0
            while (true) {
                val hashtag1Position = pos1.calculateHashtag(pos2)
                if (!hashtag1Position.isInBounds(width, height)) {
                    break
                }
                if (foundedPositions.add(hashtag1Position)) result++

                pos2 = pos1
                pos1 = hashtag1Position
            }
            return result
        }

        return charsMap.keys.sumOf { char ->
            var result = 0
            val positions = charsMap[char]!!

            for (i in positions.indices) {
                for (j in i + 1 until positions.size) {
                    val pos1 = positions[i]
                    val pos2 = positions[j]

                    if (foundedPositions.add(pos1)) result++
                    if (foundedPositions.add(pos2)) result++

                    result += findAllPossiblePositions(pos1, pos2)
                    result += findAllPossiblePositions(pos2, pos1)
                }
            }
            result
        }
    }

    val testInput = readTestInput()
    check(part1(testInput) == 14)
    check(part2(testInput) == 34)

    val input = readInput()
    part1(input).println()
    part2(input).println()

}
