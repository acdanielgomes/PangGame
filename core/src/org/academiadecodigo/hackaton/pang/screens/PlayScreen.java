package org.academiadecodigo.hackaton.pang.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.academiadecodigo.hackaton.pang.PangGame;
import org.academiadecodigo.hackaton.pang.collision.CollisionDetector;
import org.academiadecodigo.hackaton.pang.sprites.Ball;
import org.academiadecodigo.hackaton.pang.sprites.Boundary;
import org.academiadecodigo.hackaton.pang.sprites.Harpoon;
import org.academiadecodigo.hackaton.pang.sprites.Player;
import org.academiadecodigo.hackaton.pang.utilities.AnimationManager;
import org.academiadecodigo.hackaton.pang.utilities.BoundaryType;

import java.util.LinkedList;
import java.util.List;

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

    private Boundary floor;
    private Boundary top;
    private Boundary right;
    private Boundary left;
    private List<Harpoon> harpoons;

    private AnimationManager animationManager = new AnimationManager();

    private final Texture background = new Texture("background.png");

    public PlayScreen(PangGame game, AssetManager manager) {
        this.game = game;
        this.manager = manager;

        init();
    }

    private void init() {
        cam = new OrthographicCamera();
        cam.setToOrtho(false, PangGame.V_WIDTH  / PangGame.PPM, PangGame.V_HEIGHT/ PangGame.PPM);

        port = new FitViewport(PangGame.V_WIDTH / PangGame.PPM, PangGame.V_HEIGHT / PangGame.PPM, cam);
        cam.position.set(port.getWorldWidth() / 2, port.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0f, -9.8f), true);
        world.setContactListener(new CollisionDetector());

        floor = new Boundary(this, BoundaryType.FLOOR);
        top = new Boundary(this, BoundaryType.TOP);
        right = new Boundary(this, BoundaryType.RIGHT_WALL);
        left = new Boundary(this, BoundaryType.LEFT_WALL);

        balls = new Array<Ball>();
        balls.add(new Ball(this, null));
        balls.add(new Ball(this, balls.get(0)));
        balls.add(new Ball(this, balls.get(1)));
        balls.add(new Ball(this, balls.get(2)));

        renderer = new Box2DDebugRenderer();

        float playerPosY = PangGame.BOUNDARY_THICKNESS + PangGame.PLAYER_HEIGHT / 2;

        player1 = new Player(this, POS_PLAYER1, playerPosY, 1);
        player2 = new Player(this, POS_PLAYER2, playerPosY, 2);
        harpoons = new LinkedList<Harpoon>();

    }

    private void handleInput(float dt) {
        handlePlayer1Input();
        handlePlayer2Input();
    }

    private void handlePlayer1Input() {
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            player1.getBody().setLinearVelocity(-PangGame.PLAYER_SPEED, 0);
            animationManager.load(1, "L", 1);
        } else if (Gdx.input.isKeyPressed(Input.Keys.X)) {
            player1.getBody().setLinearVelocity(PangGame.PLAYER_SPEED, 0);
            animationManager.load(1, "R", 2);
        } else {
            Vector2 p1Vel = player1.getBody().getLinearVelocity();
            if (p1Vel.x != 0 || p1Vel.y != 0) {
                setToSteady(player1);
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)) {
            harpoons.add(player1.shoot());
            animationManager.load(1, "S", 3);
        }
    }

    private void handlePlayer2Input() {
        if (Gdx.input.isKeyPressed(Input.Keys.M)) {
            player2.getBody().setLinearVelocity(-PangGame.PLAYER_SPEED, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.COMMA)) {
           player2.getBody().setLinearVelocity(PangGame.PLAYER_SPEED, 0);
        } else {
            Vector2 p2Vel = player2.getBody().getLinearVelocity();
            if (p2Vel.x != 0 || p2Vel.y != 0) {
               setToSteady(player2);
           }
       }
       if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
           harpoons.add(player2.shoot());
       }
   }

    private void setToSteady(Player player) {
        player.getBody().setLinearVelocity(0, 0);
    }

    private void update(float dt) {
        handleInput(dt);
        world.step(1 / 60f,6, 2);

        for (Harpoon harpoon : harpoons) {
            harpoon.update();
        }

        for (Ball ball : balls) {
            ball.update(dt);
        }
        player1.update(dt);
        player2.update(dt);

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

        game.getBatch().setProjectionMatrix(cam.combined);
        game.getBatch().begin();

        game.getBatch().draw(background, 0, 0, PangGame.V_WIDTH / PangGame.PPM, PangGame.V_HEIGHT / PangGame.PPM);

        for (Ball ball : balls) {
            ball.draw(game.getBatch());
        }

        for (Harpoon harpoon : harpoons) {
            harpoon.draw(game.getBatch());
        }

        player1.draw(game.getBatch());
        player2.draw(game.getBatch());
        //System.out.println("Pos " + player1.getWidth() + " " + player1.getHeight() + " " + player1.getX() + " " + player1.getY());

        game.getBatch().end();

        renderer.render(world, cam.combined);
    }

    public Array<Ball> getBalls() {
        return balls;
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
