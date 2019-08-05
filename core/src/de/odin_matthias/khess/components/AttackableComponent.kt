package de.odin_matthias.khess.components

import com.badlogic.ashley.core.Component


class AttackableComponent : Component {
    var attackableBySelectedPiece: Boolean = false
}