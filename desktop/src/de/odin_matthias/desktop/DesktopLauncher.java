package de.odin_matthias.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import de.odin_matthias.khess.game.GameApplication;
import de.odin_matthias.khess.game.GameConfig;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.height = GameConfig.TILE_SIZE * GameConfig.BOARD_DIMENSION;
		config.width = GameConfig.TILE_SIZE * GameConfig.BOARD_DIMENSION;
		config.title = "Khess";
        new LwjglApplication(new GameApplication(), config);
    }
}
