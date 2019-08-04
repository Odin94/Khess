package de.odin_matthias.khess.components.movement

import com.badlogic.ashley.core.Component


class AttackComponent() : Component {
    var distance: Int = 1
    var directions: List<Directions> = Directions.values().toList()
}