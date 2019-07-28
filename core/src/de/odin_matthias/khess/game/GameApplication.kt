package de.odin_matthias.khess.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import de.odin_matthias.khess.board.Board
import de.odin_matthias.khess.resources.TextureRepository


class GameApplication() : ApplicationAdapter() {
    private lateinit var batch: SpriteBatch
    private lateinit var board: Board

    override fun create() {
        TextureRepository.load()
        batch = SpriteBatch()

        board = Board(GameConfig.BOARD_DIMENSION, GameConfig.BOARD_DIMENSION)
    }

    override fun render() {
        Gdx.gl.glClearColor(0.15F, 0.1F, 0.1F, 1F)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        board.engine.update(Gdx.graphics.deltaTime);
    }

    override fun dispose() {
        batch.dispose()
    }
}