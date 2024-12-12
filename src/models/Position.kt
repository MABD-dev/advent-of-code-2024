package models


data class Position(val r: Int, val c: Int) {

    fun all4Neighbors(): List<Position> = listOf(
        Position(r, c - 1),
        Position(r - 1, c),
        Position(r, c + 1),
        Position(r + 1, c)
    )

    fun moveRight() = this.copy(r = r, c = c + 1)
    fun moveLeft() = this.copy(r = r, c = c - 1)
    fun moveUp() = this.copy(r = r - 1, c = c)
    fun moveDown() = this.copy(r = r + 1, c = c)

    fun inBounds(width: Int, height: Int): Boolean = this.c in 0..<width && this.r in 0..<height

}
