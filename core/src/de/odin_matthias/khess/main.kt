package de.odin_matthias.khess

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import de.odin_matthias.khess.game.GameApplication

fun main(args: Array<String>) {
    val config = LwjglApplicationConfiguration()
    LwjglApplication(GameApplication(), config)
}
