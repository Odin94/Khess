package de.odin_matthias.khess.systems

import com.badlogic.ashley.core.EntitySystem
import de.odin_matthias.khess.PieceColors


object TurnSystem : EntitySystem() {
    private val colors = PieceColors.values()
    var color: PieceColors = PieceColors.WHITE

    fun nextTurn() {
        color = colors[(colors.indexOf(color) + 1) % colors.size]
    }
}