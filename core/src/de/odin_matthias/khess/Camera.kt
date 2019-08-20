package de.odin_matthias.khess

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector3
import de.odin_matthias.khess.game.GameConfig


class Camera : OrthographicCamera(GameConfig.BOARD_SIZE.toFloat(), GameConfig.BOARD_SIZE.toFloat()) {

    init {
        position.set(GameConfig.BOARD_SIZE.toFloat() / 2, GameConfig.BOARD_SIZE.toFloat() / 2, 0f)
        zoom = 1.1f
        update()
    }

    fun getMousePosInGameWorld(): Vector3 {
        return unproject(Vector3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f))
    }
}