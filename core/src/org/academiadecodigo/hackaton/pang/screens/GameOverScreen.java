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

    public GameOverScreen(PangGame game, AssetManager manager, int victoPlayer) {
        this.game = game;
        this.manager = manager;

        cam = new OrthographicCamera();
        viewPort = new FitViewport(PangGame.V_WIDTH, PangGame.V_HEIGHT, cam);

        cam.position.set(PangGame.V_WIDTH / 2, PangGame.V_HEIGHT / 2, 0);

        //music = manager.get("gameover.mp3", Music.class);
        //music.setLooping(true);
        //music.play();

        if (victoPlayer == 1) {
            texture = new Texture("Background/Background32.png");
        } else {
            texture = new Texture("Background/Background31.png");
        }
    }

    public void update(float delta) {
        keyHandler(delta);
    }

    public void keyHandler(float delta) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new MenuScreen(game, manager));
            dispose();
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().setProjectionMatrix(cam.combined);
        game.getBatch().begin();
        game.getBatch().draw(texture, 0, cam.position.y - cam.viewportHeight / 2, PangGame.V_WIDTH, PangGame.V_HEIGHT);
        game.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        viewPort.update(PangGame.V_WIDTH, PangGame.V_HEIGHT);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //texture.dispose();
        //music.dispose();
    }
}
