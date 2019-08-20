package de.odin_matthias.khess.game

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import de.odin_matthias.khess.resources.TextureRepository
import de.odin_matthias.khess.screen.MainMenuScreen


class GameApplication() : Game() {
    lateinit var batch: SpriteBatch
    lateinit var font: BitmapFont

    override fun create() {
        TextureRepository.load()
        batch = SpriteBatch()
        font = BitmapFont()

        setScreen(MainMenuScreen(this))
    }

    override fun render() {
        super.render()
    }

    override fun dispose() {
        batch.dispose()
        font.dispose()
    }
}