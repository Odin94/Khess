package de.odin_matthias.khess.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2
import de.odin_matthias.khess.game.GameConfig.TILE_SIZE


class PositionComponent() : Component {
    var x: Float = 0F
    var y: Float = 0F

    var vector: Vector2
        get() = Vector2(x, y)
        set(pos) {
            x = pos.x
            y = pos.y
        }

    var coordVector: Vector2
        get() = Vector2(x / TILE_SIZE, y / TILE_SIZE)
        set(pos) {
            x = pos.x * TILE_SIZE
            y = pos.y * TILE_SIZE
        }

    var coordX: Float
        get() = x / TILE_SIZE
        set(value) {
            x = value * TILE_SIZE
        }

    var coordY: Float
        get() = y / TILE_SIZE
        set(value) {
            y = value * TILE_SIZE
        }
}