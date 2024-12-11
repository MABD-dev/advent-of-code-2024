fun main() {

    fun part1(input: List<String>): Long {
        fun canBeSolved(
            input: List<Int>,
            index: Int,
            curr: Long,
            target: Long
        ): Boolean {
            if (index >= input.size) {
                return curr == target
            }

            // Choose to add
            var tmpCurr = curr + input[index]
            if (canBeSolved(input, index+1, tmpCurr, target)) {
                return true
            }
            tmpCurr -= input[index]

            // Choose to multiply
            tmpCurr *= input[index]
            if (canBeSolved(input, index+1, tmpCurr, target)) {
                return true
            }
            tmpCurr /= input[index]

            return false
        }


        return input.sumOf { line ->
            val (a, b) = line.split(":")
            val target = a.toLong()
            val numbers = b.trim().split(" ").map { it.toInt() }

            if (canBeSolved(numbers, 1, numbers[0].toLong(), target)) target else 0L
        }
    }

    fun part2(input: List<String>): Long {
        fun canBeSolved(
            input: List<Int>,
            index: Int,
            curr: Long,
            target: Long
        ): Boolean {
            if (index >= input.size) {
                return curr == target
            }

            // Choose to add
            var tmpCurr = curr + input[index]
            if (canBeSolved(input, index+1, tmpCurr, target)) {
                return true
            }
            tmpCurr -= input[index]

            // Choose to multiply
            tmpCurr *= input[index]
            if (canBeSolved(input, index+1, tmpCurr, target)) {
                return true
            }
            tmpCurr /= input[index]

            // Choose to concat
            tmpCurr = "$tmpCurr${input[index]}".toLong()
            return canBeSolved(input, index+1, tmpCurr, target)
        }

        return input.sumOf { line ->
            val (a, b) = line.split(":")
            val target = a.toLong()
            val numbers = b.trim().split(" ").map { it.toInt() }

            if (canBeSolved(numbers, 1, numbers[0].toLong(), target)) target else 0L
        }
    }

    val testInput = readTestInput()
    check(part1(testInput) == 3749L)
    check(part2(testInput) == 11387L)

    val input = readInput()
    part1(input).println()
    part2(input).println()


}
