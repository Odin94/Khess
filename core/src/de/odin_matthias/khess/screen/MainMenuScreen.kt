package de.odin_matthias.khess.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import de.odin_matthias.khess.game.GameApplication
import ktx.actors.stage
import ktx.scene2d.Scene2DSkin
import ktx.scene2d.label
import ktx.scene2d.table
import ktx.style.color
import ktx.style.label
import ktx.style.skin


class MainMenuScreen(val game: GameApplication, private val stage: Stage = stage()) : Screen {

    init {
        stage.camera.position.set(0f, -Gdx.graphics.height.toFloat() / 4, 0f)
        Gdx.input.inputProcessor = stage

        Scene2DSkin.defaultSkin = skin {
            color("white", 1f, 1f, 1f, 1f)
            color("black", red = 0f, green = 0f, blue = 0f)
            color("red", red = 1f, green = 0f, blue = 0f, alpha = 1f)
            label {
                font = BitmapFont().also { it.data.scale(2f) }
                fontColor = Color.CORAL
            }
        }
        stage.addActor(
                table {
                    table {
                        label("Khess")
                        it.spaceBottom(10f).row()
                    }
                    table {
                        label("Click to start") {
                            color = Color.WHITE
                        }
                    }
                }
        )
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0.15F, 0.1F, 0.1F, 1F)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.draw()

        update(delta)

        if (Gdx.input.isTouched) {
            game.screen = GameScreen(game)
            dispose()
        }
    }

    private fun update(delta: Float) {
        stage.act(delta)
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
