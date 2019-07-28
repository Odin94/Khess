package de.odin_matthias.khess.board

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity


class Board(
    private val tiles: Map<Position, Tile>
) {
    private val engine = Engine()

    fun addAt(piece: Entity, position: Position) {
        tiles.getValue(position).occupants.add(piece)
        engine.addEntity(piece)
    }
}