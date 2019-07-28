package de.odin_matthias.khess.board

import com.badlogic.ashley.core.Entity

enum class TileColor {
    BLACK,
    WHITE
}

class Tile(val color: TileColor) {
    val occupants: MutableList<Entity> = mutableListOf()

    companion object {
        fun fromPosition(row: Int, col: Int) = if (row % 2 == col % 2) Tile(TileColor.WHITE) else Tile(TileColor.BLACK)
    }
}