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
import org.academiadecodigo.hackaton.pang.screens.PlayScreen;

public class PangGame extends Game {
    public static final int V_WIDTH = 1080;
    public static final int V_HEIGHT = 600;
    public static final float PPM = 100;
    public static final float PLAYER_WIDTH = 20;
    public static final float PLAYER_HEIGHT = 50;
    public static final float PLAYER_SPEED = 4f;
    public static final float BALL_RADIUS = 12.5f;

    public SpriteBatch getBatch() {
        return batch;
    }

    private SpriteBatch batch;
    private AssetManager manager;

    @Override
    public void create() {
        batch = new SpriteBatch();

        setScreen(new PlayScreen(this, manager));

    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }
}
