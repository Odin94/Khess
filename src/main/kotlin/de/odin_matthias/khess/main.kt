package de.odin_matthias.khess

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import de.odin_matthias.khess.view.GameApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration

fun main(args: Array<String>) {
    val application = GameApplication()
    val config = LwjglApplicationConfiguration()
    LwjglApplication(GameApplication(), config)
}
