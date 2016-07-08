package org.academiadecodigo.hackaton.pang;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.academiadecodigo.hackaton.pang.screens.MenuScreen;

public class PangGame extends Game {
    public static final int V_WIDTH = 1080;
    public static final int V_HEIGHT = 600;
    public static final float PPM = 100;
    public static final float PLAYER_WIDTH = 60;
    public static final float PLAYER_HEIGHT = 75;
    public static final float PLAYER_SPEED = 3f;
    public static final float POS_PLAYER1 = 300;
    public static final float POS_PLAYER2 = 800;
    public static final float HARPOON_WIDTH = 20;
    public static final float HARPOON_HEIGHT = V_HEIGHT;
    public static final float HARPOON_SPEED = 4f;
    public static final float BALL_RADIUS = 20f;
    public static final float BALL_SPEED = 2f;
    public static final float BOUNDARY_THICKNESS = 23;
    public static final long BALL_SPAWN_TIME = 5;
    public static final float BALL_SPAWN_HEIGHT = 500;
    public static final float SMALL_BALLS_VERTICAL_IMPULSE = 4f;

    public static final int PORT_PLAYER1 = 10001;
    public static final int PORT_PLAYER2 = 10002;

    public static final short BALL_BIT = 1;
    public static final short PLAYER_BIT = 2;
    public static final short HARPOON_BIT = 8;
    public static final short BOUNDARY_BIT = 16;

    // Draw textures
    private SpriteBatch batch;

    // Responsible for playing sounds and music
    private AssetManager manager;

    @Override
    /**
     * @see Game#create()
     */
    public void create() {

        batch = new SpriteBatch();

        manager = new AssetManager();
        manager.load("SoundTrack.mp3", Music.class);
        manager.load("BubblePoP.mp3", Sound.class);
        manager.load("GunClank.mp3", Sound.class);
        manager.finishLoading();


        setScreen(new MenuScreen(this, manager));
    }

    @Override
    /**
     * @see Game#render() ()
     */
    public void render() {
        super.render();
    }

    @Override
    /**
     * @see Game#dispose()
     */
    public void dispose() {
        super.dispose();
        batch.dispose();
        manager.dispose();
    }

    /**
     * Getter
     *
     * @return
     */
    public SpriteBatch getBatch() {
        return batch;
    }
}
