package de.odin_matthias.khess

import com.badlogic.ashley.core.Engine
import de.odin_matthias.khess.components.PositionComponent
import de.odin_matthias.khess.components.VisualComponent
import de.odin_matthias.khess.resources.TextureRepository
import ktx.ashley.entity


object EntityFactory {
    fun addTile(engine: Engine, xPos: Float, yPos: Float) =
        engine.entity {
            with<VisualComponent>() {
                texture = getTexture(xPos.toInt(), yPos.toInt())
            }
            with<PositionComponent> {
                x = xPos
                y = yPos
            }
        }
}

fun getTexture(row: Int, col: Int) =
    if (row % 2 == col % 2) TextureRepository.WHITE_TILE else TextureRepository.BLACK_TILE