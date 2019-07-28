package de.odin_matthias.khess.board

import com.badlogic.ashley.core.Engine
import de.odin_matthias.khess.EntityFactory.addTile
import de.odin_matthias.khess.systems.RenderSystem


class Board(private val width: Int, private val height: Int) {
    val engine = Engine()

    init {
        generateEntities()
        generateSystems()
    }

    private fun generateEntities() {
        generateTiles(width, height)
    }

    private fun generateSystems() {
        engine.addSystem(RenderSystem())
    }

    private fun generateTiles(width: Int, height: Int) = also {
        for (row in 0 until width) {
            for (col in 0 until height) {
                addTile(engine, row.toFloat(), col.toFloat())
            }
        }
    }
}