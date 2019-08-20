package de.odin_matthias.khess.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import de.odin_matthias.khess.InputReceiver
import de.odin_matthias.khess.board.Board
import de.odin_matthias.khess.game.GameApplication
import de.odin_matthias.khess.game.GameConfig


class GameScreen(val game: GameApplication, private val board: Board = Board(GameConfig.BOARD_DIMENSION, GameConfig.BOARD_DIMENSION)) : Screen {

    init {
        Gdx.input.inputProcessor = InputReceiver(board.engine)
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0.15F, 0.1F, 0.1F, 1F)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        board.engine.update(Gdx.graphics.deltaTime)
    }

    override fun show() {
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun dispose() {
    }

    override fun hide() {
    }
}