package org.academiadecodigo.hackaton.pang.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.academiadecodigo.hackaton.pang.PangGame;
import org.academiadecodigo.hackaton.pang.collision.CollisionDetector;
import org.academiadecodigo.hackaton.pang.sprites.Ball;
import org.academiadecodigo.hackaton.pang.sprites.Player;

/**
 * Created by codecadet on 07/07/16.
 */
public class PlayScreen implements Screen {

    private final float POS_PLAYER1 = 300;
    private final float POS_PLAYER2 = 800;

    private PangGame game;
    private AssetManager manager;

    private OrthographicCamera cam;
    private Viewport port;

    private World world;
    private Box2DDebugRenderer renderer;

    private Array<Ball> balls;
    private Player player1;
    private Player player2;

    public PlayScreen(PangGame game, AssetManager manager) {
        this.game = game;
        this.manager = manager;

        init();
    }

    private void init() {
        cam = new OrthographicCamera();
        cam.setToOrtho(false, PangGame.V_WIDTH / 2 / PangGame.PPM, PangGame.V_HEIGHT / 2 / PangGame.PPM);

        port = new FitViewport(PangGame.V_WIDTH / PangGame.PPM, PangGame.V_HEIGHT / PangGame.PPM, cam);
        cam.position.set(port.getWorldWidth() / 2, port.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0f, -9.8f), true);
        world.setContactListener(new CollisionDetector());

        balls = new Array<Ball>();
        balls.add(new Ball(this));

        renderer = new Box2DDebugRenderer();
        player1 = new Player(this, POS_PLAYER1, 200);
//        player2 = new Player(this, 0, POS_PLAYER2);

    }

    private void handleInput(float dt) {
        handlePlayer1Input();
//        handlePlayer2Input();
    }
//
    private void handlePlayer1Input() {
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            player1.getBody().setLinearVelocity(-PangGame.PLAYER_SPEED, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.X)) {
            player1.getBody().setLinearVelocity(PangGame.PLAYER_SPEED, 0);
        } else {
            Vector2 p1Vel = player1.getBody().getLinearVelocity();
            if (p1Vel.x != 0 || p1Vel.y != 0) {
                setToSteady(player1);
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)) {
            //player1.shoot();
            System.out.println("shoot");
        }
    }

//    private void handlePlayer2Input() {
//        if (Gdx.input.isKeyPressed(Input.Keys.M)) {
//            player2.getBody().setLinearVelocity(-PangGame.PLAYER_SPEED, 0);
//        } else if (Gdx.input.isKeyPressed(Input.Keys.COMMA)) {
//            player2.getBody().setLinearVelocity(PangGame.PLAYER_SPEED, 0);
//        } else {
//            Vector2 p2Vel = player2.getBody().getLinearVelocity();
//            if (p2Vel.x != 0 || p2Vel.y != 0) {
//                setToSteady(player2);
//            }
//        }
//        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
//            player2.shoot();
//        }
//    }
//
    private void setToSteady(Player player) {
        player.getBody().setLinearVelocity(0, 0);
    }

    private void update(float dt) {
        handleInput(dt);
        world.step(1 / 60f,6, 2);

        for (Ball ball : balls) {
            ball.update(dt);
        }
        player1.update(dt);
//        player2.update(dt);

        cam.update();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        //renderer.render(world, cam.combined);

        game.getBatch().setProjectionMatrix(cam.combined);
        game.getBatch().begin();

        //balls.get(0).draw(game.getBatch());
        player1.draw(game.getBatch());
        System.out.println("Pos " + player1.getWidth() + " " + player1.getHeight() +
        " " + player1.getX() + " " + player1.getY());

        game.getBatch().end();
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void resize(int width, int height) {

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

    }
}
