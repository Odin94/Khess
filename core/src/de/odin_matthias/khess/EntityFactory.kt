package de.odin_matthias.khess

import com.badlogic.ashley.core.Engine
import de.odin_matthias.khess.components.PositionComponent
import de.odin_matthias.khess.components.VisualComponent
import de.odin_matthias.khess.game.GameConfig.TILE_SIZE
import de.odin_matthias.khess.resources.TextureRepository.BLACK_TILE
import de.odin_matthias.khess.resources.TextureRepository.WHITE_TILE
import ktx.ashley.entity


object EntityFactory {
    fun addTile(engine: Engine, row: Float, col: Float) =
            engine.entity {
                with<VisualComponent>() {
                    texture = getTexture(row.toInt(), col.toInt())
                }
                with<PositionComponent> {
                    x = row * TILE_SIZE
                    y = col * TILE_SIZE
                }
            }
}

fun getTexture(row: Int, col: Int) =
        if (row % 2 == col % 2) WHITE_TILE else BLACK_TILE