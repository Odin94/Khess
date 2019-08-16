package de.odin_matthias.khess.components.movement

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity


class WalkableComponent : Component {
    var walkableBySelectedPiece: Boolean = false
    var castleableBy: Castlers? = null
}

data class Castlers(val castler: Entity, val castleTarget: Entity)