package de.odin_matthias.khess.board

import com.badlogic.ashley.core.Engine
import de.odin_matthias.khess.EntityFactory.addTile

const val tileSize = 64

class Board(private val width: Int, private val height: Int) {
    val engine = Engine()

    init {
        generateTiles(width, height)
    }

    private fun generateTiles(width: Int, height: Int) = also {
        (0 until width).zip(0 until height).forEach { (x, y) ->
            addTile(engine, x.toFloat() * tileSize, y.toFloat() * tileSize)
        }
    }
}