

fun main() {
    val input = readInput("input")

    val grid: List<CharArray> = input.map { it.toCharArray() }
    val start: Point2D = grid
        .flatMapIndexed { y, row ->
            row.mapIndexed { x, c ->
                if (c == '^') Point2D(x, y) else null
            }
        }.filterNotNull().first()

    solvePart1(grid, start).println()
    solvePart2(grid, start).println()
}

fun solvePart1(
    grid: List<CharArray>,
    start: Point2D
): Int = traverse(grid, start).first.size

fun solvePart2(
    grid: List<CharArray>,
    start: Point2D
): Int =
    traverse(grid, start)
        .first
        .filterNot { it == start }
        .count { candidate ->
            grid[candidate] = '#'
            traverse(grid, start).also { grid[candidate] = '.' }.second
        }

private fun traverse(
    grid: List<CharArray>,
    start: Point2D
): Pair<Set<Point2D>, Boolean> {
    val seen = mutableSetOf<Pair<Point2D, Point2D>>()
    var location = start
    var direction = Point2D.NORTH

    while (grid[location] != null && (location to direction) !in seen) {
        seen += location to direction
        val next = location + direction

        if (grid[next] == '#') direction = direction.turn()
        else location = next
    }
    return seen.map { it.first }.toSet() to (grid[location] != null)
}

private operator fun List<CharArray>.get(at: Point2D): Char? =
    getOrNull(at.y)?.getOrNull(at.x)

private operator fun List<CharArray>.set(at: Point2D, c: Char) {
    this[at.y][at.x] = c
}

private fun Point2D.turn(): Point2D =
    when (this) {
        Point2D.NORTH -> Point2D.EAST
        Point2D.EAST -> Point2D.SOUTH
        Point2D.SOUTH -> Point2D.WEST
        Point2D.WEST -> Point2D.NORTH
        else -> throw IllegalStateException("Bad direction: $this")
    }


data class Point2D(val x: Int, val y: Int) {

    fun cardinalNeighbors(): Set<Point2D> =
        setOf(
            this + NORTH,
            this + EAST,
            this + SOUTH,
            this + WEST
        )

    operator fun plus(other: Point2D): Point2D =
        Point2D(x + other.x, y + other.y)

    operator fun minus(other: Point2D): Point2D =
        Point2D(x - other.x, y - other.y)

    companion object {
        val NORTH = Point2D(0, -1)
        val EAST = Point2D(1, 0)
        val SOUTH = Point2D(0, 1)
        val WEST = Point2D(-1, 0)
    }
}