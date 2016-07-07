package org.academiadecodigo.hackaton.pang;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.academiadecodigo.hackaton.pang.screens.MenuScreen;

public class PangGame extends Game{
	public static final int V_WIDTH = 1080;
	public static final int V_HEIGHT = 600;

	public SpriteBatch batch;
	private AssetManager manager;

	@Override
	public void create () {
		batch = new SpriteBatch();

		setScreen(new MenuScreen(this, manager));

	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
	}
}
