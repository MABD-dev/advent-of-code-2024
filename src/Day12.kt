import models.Position

private fun Boolean.toInt() = if(this) 1 else 0

private data class Cell(
    val visited: Boolean = false,
    val hasLeft: Boolean = true,
    val hasTop: Boolean = true,
    val hasRight: Boolean = true,
    val hasBoolean: Boolean = true
) {
    val sides: Int = hasLeft.toInt() + hasTop.toInt() + hasRight.toInt() + hasBoolean.toInt()
}

private fun search(
    input: List<List<String>>,
    width: Int,
    height: Int,
    pos: Position,
    s: String,
    searchResult: MutableSet<Position>
) {
    for (newPos in pos.all4Neighbors()) {
        if (newPos.inBounds(width, height) && newPos !in searchResult && input[newPos.r][newPos.c] == s) {
            searchResult.add(newPos)
            search(input, width, height, newPos, s, searchResult)
        }
    }
}

private fun calculateTotalScore(searchResult: MutableSet<Position>): Long {
    val area = searchResult.size
    var parameter = 0L
    searchResult.forEach { pos ->
        var acc = 4L
        for (newPos in pos.all4Neighbors()) {
            if (newPos in searchResult) acc--
        }
        parameter += acc
    }

    return area * parameter
}


private fun calculateTotalScorePart2(searchResult: MutableSet<Position>, width: Int, height: Int): Long {
    val area = searchResult.size

    val edges = mutableSetOf<Position>()

    searchResult.forEach { pos ->
        if (pos.moveRight() in searchResult) {

        }
        if (pos.moveLeft() in searchResult) {

        }
        if (pos.moveUp() in searchResult) {

        }
        if (pos.moveDown() in searchResult) {

        }
    }


    return area.toLong()
}

private fun part1(input: List<String>): Long {
    val strInput = input.map { line -> line.toCharArray().map { it.toString() } }
    val width = strInput[0].size
    val height = strInput.size

    val searchResult = mutableSetOf<Position>()
    var scoreSum = 0L

    for (i in 0 until height) {
        for (j in 0 until width) {
            val pos = Position(i, j)
            if (pos !in searchResult) {
                val strSearchResult = mutableSetOf(pos)
                search(strInput, width, height, pos, strInput[i][j], strSearchResult)
                scoreSum += calculateTotalScore(strSearchResult)
                searchResult.addAll(strSearchResult)
            }
        }
    }
    return scoreSum
}

private fun part2(input: List<String>): Long {
    val strInput = input.map { line -> line.toCharArray().map { it.toString() } }
    val width = strInput[0].size
    val height = strInput.size

    val searchResult = mutableSetOf<Position>()
    var scoreSum = 0L
    println("width=$width, height=$height")

    for (i in 0 until height) {
        for (j in 0 until width) {
            val pos = Position(i, j)
            if (pos !in searchResult) {
                val strSearchResult = mutableSetOf(pos)
                search(strInput, width, height, pos, strInput[i][j], strSearchResult)
                scoreSum += calculateTotalScorePart2(strSearchResult, width, height)
                searchResult.addAll(strSearchResult)
            }
        }
    }

    return scoreSum.also { println("total sum = $it") }
}

fun main() {

    val testInput = readTestInput()
    check(part1(testInput) == 1930L)
//    check(part2(testInput) == 8L)

    val input = readInput()
    part1(input).println()
//    part2(input).println()


}
