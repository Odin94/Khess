package de.odin_matthias.khess.resources

import com.badlogic.gdx.graphics.Texture


object TextureRepository {
    const val pathPrefix = "data"

    lateinit var BLACK_TILE: Texture
    lateinit var WHITE_TILE: Texture

    fun load() {
        BLACK_TILE = Texture("badlogic.jpg") //Texture("$pathPrefix/black_tile.png")
        WHITE_TILE = Texture("badlogic.jpg") //Texture("$pathPrefix/white_tile.png")
    }
}