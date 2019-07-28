package de.odin_matthias.khess.resources

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture


object TextureRepository {
    const val pathPrefix = "resources"

    val BLACK_TILE = Texture(Gdx.files.internal("$pathPrefix/black_tile.png"))
    val WHITE_TILE = Texture(Gdx.files.internal("$pathPrefix/white_tile.png"))
}