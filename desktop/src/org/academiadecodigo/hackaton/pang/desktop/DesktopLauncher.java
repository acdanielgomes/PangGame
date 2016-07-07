package org.academiadecodigo.hackaton.pang.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.academiadecodigo.hackaton.pang.PangGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = PangGame.V_WIDTH;
		config.height = PangGame.V_HEIGHT;
		config.title = "SUPER HIPER MEGA PANG";
		new LwjglApplication(new PangGame(), config);
	}
}
