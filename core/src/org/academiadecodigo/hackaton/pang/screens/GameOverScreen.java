package org.academiadecodigo.hackaton.pang.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.academiadecodigo.hackaton.pang.PangGame;

/**
 * Created by codecadet on 07/07/16.
 */
public class GameOverScreen implements Screen {

    private PangGame game;
    private OrthographicCamera cam;
    private Viewport viewPort;

    private AssetManager manager;
    private Music music;

    private Texture texture;
    private Texture text;

    /**
     * Constructor method
     * Create a new cam and a new view port
     * sets textures
     *
     * @param game Main class
     * @param manager Class responsible for playing sounds and music
     * @param victoPlayer Identifier of the player who won
     */
    public GameOverScreen(PangGame game, AssetManager manager, int victoPlayer) {
        this.game = game;
        this.manager = manager;

        cam = new OrthographicCamera();
        viewPort = new FitViewport(PangGame.V_WIDTH, PangGame.V_HEIGHT, cam);

        cam.position.set(PangGame.V_WIDTH / 2, PangGame.V_HEIGHT / 2, 0);

        //music = manager.get("gameover.mp3", Music.class);
        //music.setLooping(true);
        //music.play();

        texture = new Texture("WinLoose/P" + victoPlayer + "Wins.png");
        text = new Texture("WinLoose/P" + victoPlayer + "WinsLettering.png");

    }

    public void update() {
        handleInput();
    }

    /**
     * Is listening for a specific input by the player
     */
    public void handleInput() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new MenuScreen(game, manager));
            dispose();
        }
    }

    @Override
    /**
     * @see Screen#show()
     */
    public void show() {

    }

    @Override
    /**
     * @see Screen#render(float)
     */
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update();

        game.getBatch().setProjectionMatrix(cam.combined);
        game.getBatch().begin();

        game.getBatch().draw(texture, 0, cam.position.y - cam.viewportHeight / 2, PangGame.V_WIDTH, PangGame.V_HEIGHT);
        game.getBatch().draw(text, cam.position.x - text.getWidth() / 2, cam.position.y - 270);

        game.getBatch().end();
    }

    @Override
    /**
     * @see Screen#resize(int, int)
     */
    public void resize(int width, int height) {
        viewPort.update(PangGame.V_WIDTH, PangGame.V_HEIGHT);
    }

    @Override
    /**
     * @see Screen#pause()
     */
    public void pause() {

    }

    @Override
    /**
     * @see Screen#resume()
     */
    public void resume() {

    }

    @Override
    /**
     * @see Screen#hide()
     */
    public void hide() {

    }

    @Override
    /**
     * @see Screen#dispose()
     */
    public void dispose() {
        texture.dispose();
        text.dispose();
        //music.dispose();
    }
}
