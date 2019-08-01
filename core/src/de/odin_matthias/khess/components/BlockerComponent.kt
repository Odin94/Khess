package de.odin_matthias.khess.components

import com.badlogic.ashley.core.Component
import de.odin_matthias.khess.PIECE_COLOR


class BlockerComponent() : Component {
    var color: PIECE_COLOR = PIECE_COLOR.WHITE
}