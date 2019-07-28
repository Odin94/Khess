package de.odin_matthias.khess.resources

import com.badlogic.gdx.graphics.Texture


object TextureRepository {
    const val pathPrefix = "data"

    val BLACK_TILE = Texture("$pathPrefix/black_tile.png")
    val WHITE_TILE = Texture("badlogic.jpg") //Texture("$pathPrefix/white_tile.png")
}