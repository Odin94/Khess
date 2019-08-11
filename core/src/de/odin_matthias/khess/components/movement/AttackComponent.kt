package de.odin_matthias.khess.components.movement

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2


class AttackComponent() : Component {
    var distance: Int = 1
    var directions: List<Directions> = listOf()
    var jumpAttacks: List<Vector2> = listOf()
}