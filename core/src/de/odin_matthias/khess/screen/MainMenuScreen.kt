package de.odin_matthias.khess.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import de.odin_matthias.khess.Camera
import de.odin_matthias.khess.game.GameApplication


class MainMenuScreen(val game: GameApplication, private val camera: Camera = Camera()) : Screen {
    override fun render(delta: Float) {

        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        camera.update()
        game.batch.projectionMatrix = camera.combined

        game.batch.begin()
        game.font.draw(game.batch, "Khess - Click to play!", 100f, 150f)
        game.batch.end()

        if (Gdx.input.isTouched) {
            game.screen = GameScreen(game)
            dispose()
        }
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
