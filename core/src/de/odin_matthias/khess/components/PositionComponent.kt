package de.odin_matthias.khess.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2
import de.odin_matthias.khess.game.GameConfig.TILE_SIZE


class PositionComponent() : Component {
    var x: Float = 0F
    var y: Float = 0F

    val vector: Vector2
        get() = Vector2(x, y)

    val coordVector: Vector2
        get() = Vector2(x / TILE_SIZE, y / TILE_SIZE)
}