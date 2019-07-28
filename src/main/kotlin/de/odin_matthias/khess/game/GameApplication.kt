package de.odin_matthias.khess.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import de.odin_matthias.khess.board.Board


class GameApplication() : ApplicationAdapter() {
    private lateinit var batch: SpriteBatch
    private val board = Board(8, 8)

    override fun create() {
        batch = SpriteBatch()
    }

    override fun render() {
        Gdx.gl.glClearColor(0F, 0F, 0F, 1F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        board.engine.update(Gdx.graphics.deltaTime);
    }

    override fun dispose() {
        batch.dispose()
    }
}