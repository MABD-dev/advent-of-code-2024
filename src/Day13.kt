import models.Position


private data class MoveAddition(
    val r: Long,
    val c: Long
)

private data class Game(
    val buttonA: MoveAddition,
    val buttonB: MoveAddition,
    val prizePos: Position
)

private data class GameResult(
    val buttonACount: Int,
    val buttonBCount: Int
)

private fun part1(games: List<Game>): Long {
    return games.sumOf { game ->
        var totalTokens = 0L
        outer@ for (ac in 0 until 100) {
            for (bc in 0 until 100) {
                val newC = ac * game.buttonA.c + bc * game.buttonB.c
                val newR = ac * game.buttonA.r + bc * game.buttonB.r
                if (newC == game.prizePos.c.toLong() && newR == game.prizePos.r.toLong()) {
                    totalTokens += ac * 3 + bc
                    break@outer
                }
            }
        }
        totalTokens
    }
}

private fun part2(input: List<String>): Long {
    return 0L
}

private fun parseLine(line: String): Pair<Long, Long> {
    val colonPosition = line.indexOf(":")
    val a = line.removeRange(0..colonPosition.plus(1)).split(",").map {
        it.trim().removeRange(0..1).toLong()
    }
    return a[0] to a[1]
}

private fun parseInput(input: List<String>): List<Game> {
    var buttonA = MoveAddition(0, 0)
    var buttonB = MoveAddition(0, 0)
    var prizePos = Position(0, 0)

    val games = mutableListOf<Game>()

    input.forEach { line ->
        when {
            line.startsWith("Button A", true) -> {
                val (x, y) = parseLine(line)
                buttonA = MoveAddition(x, y)
            }
            line.startsWith("Button B", true) -> {
                val (x, y) = parseLine(line)
                buttonB = MoveAddition(x, y)
            }
            line.startsWith("Prize") -> {
                val (x, y) = parseLine(line)
                prizePos = Position(x.toInt(), y.toInt())
                games.add(Game(buttonA, buttonB, prizePos))
            }
            else -> Unit
        }
    }
    return games
}


private fun shitTry(input: List<String>): Long {
    var machineIndex = 0

    val solutions = mutableListOf<Triple<Long, Long, Long>>() // (a presses, b presses, tokens)
    val offset = 10000000000000L // Part 2 offset

    while (machineIndex < input.size) {
        // Parse each machine's configuration
        val buttonA = input[machineIndex].split(": ")[1].split(", ")
        val aX = buttonA[0].substring(2).toLong()
        val aY = buttonA[1].substring(2).toLong()

        val buttonB = input[machineIndex + 1].split(": ")[1].split(", ")
        val bX = buttonB[0].substring(2).toLong()
        val bY = buttonB[1].substring(2).toLong()

        val prize = input[machineIndex + 2].split(": ")[1].split(", ")
        val targetX = prize[0].substring(2).toLong() + offset
        val targetY = prize[1].substring(2).toLong() + offset

        // Set up system of linear equations:
        // aX * a + bX * b = targetX  (equation 1)
        // aY * a + bY * b = targetY  (equation 2)

        // Using substitution method:
        // From equation 1: b = (targetX - aX * a) / bX
        // Substitute into equation 2:
        // aY * a + bY * ((targetX - aX * a) / bX) = targetY

        // Solve for a:
        // aY * a + (bY * targetX)/bX - (bY * aX * a)/bX = targetY
        // a * (aY - (bY * aX)/bX) = targetY - (bY * targetX)/bX
        // a = (targetY - (bY * targetX)/bX) / (aY - (bY * aX)/bX)

        val denominator = aY - (bY.toDouble() * aX) / bX
        if (denominator != 0.0) {
            val a = (targetY - (bY.toDouble() * targetX) / bX) / denominator.toFloat()

            // Calculate b using equation 1
            val b = (targetX - aX * a) / bX.toFloat()
            println("a=$a, b=$b")

            // Check if solution is valid (whole numbers and positive)
            if (a >= 0 && b >= 0 && a.toLong().toDouble() == a && b.toLong().toDouble() == b) {
                val tokens = a.toLong() * 3 + b.toLong()
                solutions.add(Triple(a.toLong(), b.toLong(), tokens))
                println("Machine ${machineIndex/4 + 1}: ${a.toLong()} A presses, ${b.toLong()} B presses, $tokens tokens")
            } else {
                println("Machine ${machineIndex/4 + 1}: No valid solution")
            }
        } else {
            println("Machine ${machineIndex/4 + 1}: No solution (parallel lines)")
        }

        machineIndex += 4 // Move to next machine (skip blank line)
    }

    if (solutions.isNotEmpty()) {
        val totalTokens = solutions.sumOf { it.third }
        println("Total prizes possible: ${solutions.size}")
        println("Total tokens needed: $totalTokens")
    } else {
        println("No solutions found")
    }

    return solutions.sumOf { it.third }
}

fun main() {

    val testInput = readTestInput()
    val games = parseInput(testInput) //.onEachIndexed { index, game ->
//        println("Game ${index+1}")
//        println("Button A=${game.buttonA}")
//        println("Button B=${game.buttonB}")
//        println("Prize position=${game.prizePos}")
//        println("**********************")
//    }

//    part1(games).println()
    shitTry(testInput).println()

//    check(part1(games) == 1930L)
//    check(part2(testInput) == 8L)

    val input = readInput()
//    part1(input).println()
//    part2(input).println()


}
