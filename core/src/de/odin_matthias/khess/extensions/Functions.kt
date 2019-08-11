package de.odin_matthias.khess.extensions

import com.badlogic.gdx.math.Vector2
import de.odin_matthias.khess.game.GameConfig


fun isWithinBounds(pos: Vector2): Boolean {
    return pos.x >= 0 && pos.y >= 0 && pos.x <= GameConfig.BOARD_DIMENSION && pos.y <= GameConfig.BOARD_DIMENSION
}

fun isPointInTile(point: Vector2, tilePos: Vector2): Boolean {
    return point.x > tilePos.x && point.x < tilePos.x + GameConfig.TILE_SIZE && point.y > tilePos.y && point.y < tilePos.y + GameConfig.TILE_SIZE
}