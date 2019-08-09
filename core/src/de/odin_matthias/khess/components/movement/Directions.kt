package de.odin_matthias.khess.components.movement

import com.badlogic.gdx.math.Vector2
import de.odin_matthias.khess.PieceColors


enum class Directions {
    FORWARD,
    BACKWARD,
    LEFT,
    RIGHT,
    FORWARD_RIGHT,
    BACKWARD_RIGHT,
    FORWARD_LEFT,
    BACKWARD_LEFT
}

typealias DirectionsToNumbers = Map<Directions, Vector2>

val directionsToNumbersTop = mapOf(
        Directions.FORWARD to Vector2(0F, -1F),
        Directions.BACKWARD to Vector2(0F, 1F),
        Directions.LEFT to Vector2(-1F, 0F),
        Directions.RIGHT to Vector2(1F, 0F),
        Directions.FORWARD_RIGHT to Vector2(1F, 1F),
        Directions.BACKWARD_RIGHT to Vector2(1F, -1F),
        Directions.FORWARD_LEFT to Vector2(-1F, 1F),
        Directions.BACKWARD_LEFT to Vector2(-1F, -1F)
)

val directionsToNumbersBottom = directionsToNumbersTop.mapValues { (_, value) ->
    Vector2(value.x, -value.y)
}

val colorToDirection = mapOf(
        PieceColors.WHITE to directionsToNumbersTop,
        PieceColors.BLACK to directionsToNumbersBottom
)