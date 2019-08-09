package de.odin_matthias.khess.components

import com.badlogic.ashley.core.Component
import de.odin_matthias.khess.PieceColors


class ColorComponent() : Component {
    var color: PieceColors = PieceColors.WHITE
}