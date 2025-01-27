import kotlin.math.abs


fun List<String>.toPointMap(): Map<Vec2D, Char> {
    val map = mutableMapOf<Vec2D, Char>()
    for (y in this.indices) {
        for (x in this[y].indices) {
            map[Vec2D(x, y)] = this[y][x]
        }
    }
    return map
}



fun main() {
//    fun part1(): Any {
//        return calculateFenceCost().first
//    }
//
//    fun part2(): Any {
//        return calculateFenceCost().second
//    }

    val input = readInput()
    val map = input.toPointMap()
    val maxX = map.keys.maxOf { it.x }
    val maxY = map.keys.maxOf { it.y }

    fun calculateFenceCost(
    ): Pair<Long, Long> {
        val visited = mutableSetOf<Vec2D>()
        var count = 0L
        var countB = 0L
        for (x in 0..maxX) {
            for (y in 0..maxY) {
                val point = Vec2D(x, y)
                if (point in visited) continue
                val currentIdentifier = map[point]!!
                val queue = mutableListOf(point)
                val region = mutableSetOf<Vec2D>()
                val edges = mutableListOf<Pair<Vec2D, Vec2D>>()
                while (queue.isNotEmpty()) {
                    val current = queue.removeFirst()
                    if (current in region) continue
                    region.add(current)
                    for (neighbour in current.getNeighbours(true)) {
                        if (map[neighbour] != currentIdentifier) {
                            edges.add(current to (neighbour - current))
                            continue
                        }
                        queue.add(neighbour)
                    }
                }
                count += region.size * edges.size
                var amountOfEdges = 0
                while (edges.isNotEmpty()) {
                    val (current, direction) = edges.removeFirst()
                    amountOfEdges++
                    // check positive side of direction
                    var i = 1
                    var offset = Vec2D(direction.y, direction.x)
                    while (edges.contains((current + offset * i) to direction)) {
                        edges.remove((current + offset * i) to direction)
                        i++
                    }
                    // check negative side of direction
                    i = -1
                    while (edges.contains((current + offset * i) to direction)) {
                        edges.remove((current + offset * i) to direction)
                        i--
                    }
                }
                countB += region.size * amountOfEdges
                visited.addAll(region)
            }
        }
        return count to countB
    }



    val result = calculateFenceCost()
    println(result.first)
    println(result.second)
}




data class Vec2D(val x: Int, val y: Int) {

    companion object {
        val UP = Vec2D(0, -1)
        val LEFT = Vec2D(-1, 0)
        val RIGHT = Vec2D(1, 0)
        val DOWN = Vec2D(0, 1)
    }

    /**
     * Checks if the [other] Vec2D is a direct neighbour of this Vec2D.
     */
    fun isNeighbour(other: Vec2D) = abs(other.x - x) <= 1 && abs(other.y - y) <= 1

    /**
     * Calculates the manhattan distance between this Vec2D and the [other] Vec2D.
     */
    fun manhattanDistance(other: Vec2D) = abs(other.x - x) + abs(other.y - y)

    /**
     * Returns a list of all neighbours of this Vec2D.
     */
    fun getNeighbours(direct: Boolean = false) = buildList {
        for (xChange in -1..1) {
            for (yChange in -1..1) {
                // the middle point is not a neighbour
                if (xChange == 0 && yChange == 0) continue
                if (direct && abs(xChange) + abs(yChange) == 2) continue
                add(Vec2D(x + xChange, y + yChange))
            }
        }
    }

    operator fun plus(other: Vec2D) = Vec2D(x + other.x, y + other.y)

    operator fun minus(other: Vec2D) = Vec2D(x - other.x, y - other.y)

    operator fun times(times: Int) = Vec2D(x * times, y * times)
}

operator fun <T> List<List<T>>.get(point: Vec2D): T = this[point.y][point.x]

operator fun <T> List<MutableList<T>>.set(point: Vec2D, t: T) {
    this[point.y][point.x] = t
}

fun <T> List<List<T>>.getOrNull(point: Vec2D): T? = this.getOrNull(point.y)?.getOrNull(point.x)