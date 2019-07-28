package de.odin_matthias.khess

import com.badlogic.ashley.core.Engine
import de.odin_matthias.khess.components.PositionComponent
import de.odin_matthias.khess.components.VisualComponent
import de.odin_matthias.khess.resources.TextureRepository
import ktx.ashley.entity

const val tileSize = 64


object EntityFactory {
    fun addTile(engine: Engine, row: Float, col: Float) =
        engine.entity {
            with<VisualComponent>() {
                texture = getTexture(row.toInt(), col.toInt())
            }
            with<PositionComponent> {
                x = row * tileSize
                y = col * tileSize
            }
        }
}

fun getTexture(row: Int, col: Int) =
    if (row % 2 == col % 2) TextureRepository.WHITE_TILE else TextureRepository.BLACK_TILE