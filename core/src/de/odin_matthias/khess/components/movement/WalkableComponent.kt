package de.odin_matthias.khess.components.movement

import com.badlogic.ashley.core.Component


class WalkableComponent : Component {
    var walkableBySelectedPiece: Boolean = false
}