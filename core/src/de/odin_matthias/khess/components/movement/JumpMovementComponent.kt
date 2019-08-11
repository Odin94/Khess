package de.odin_matthias.khess.components.movement

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2


class JumpMovementComponent : Component {
    var movements: List<Vector2> = knightMovements
}

val knightMovements = listOf(
        Vector2(2F, 1F),
        Vector2(2F, -1F),
        Vector2(1F, 2F),
        Vector2(1F, -2F),

        Vector2(-2F, 1F),
        Vector2(-2F, -1F),
        Vector2(-1F, 2F),
        Vector2(-1F, -2F)
)