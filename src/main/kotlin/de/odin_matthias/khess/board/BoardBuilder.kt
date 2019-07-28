package de.odin_matthias.khess.board


class BoardBuilder() {
    private val width = 8
    private val height = 8
    private var tiles: Map<Position, Tile> = mapOf()

    fun build() = Board(width, height, tiles)

    fun generateTiles() = also {
        tiles = (0 until width).zip(0 until height).associate {
            Position(it.first, it.second) to Tile.fromPosition(it.first, it.second)
        }
    }
}