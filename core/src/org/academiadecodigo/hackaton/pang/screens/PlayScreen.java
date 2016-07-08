package org.academiadecodigo.hackaton.pang.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import org.academiadecodigo.hackaton.pang.utilities.BoundaryType;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by codecadet on 07/07/16.
 */
public class PlayScreen implements Screen {

    private final Texture background = new Texture("Background/background" + MathUtils.random(1, 32) + ".png");

    private PangGame game;
    private AssetManager manager;

    private OrthographicCamera cam;
    private Viewport port;

    private World world;

    // Used for debug
    private Box2DDebugRenderer renderer;

    private Array<Ball> balls;
    private Player player1;
    private Player player2;

    private List<Harpoon> harpoons;

    // Last time it create a ball
    private long lastSpawn;

    private Sound shootSound;
    private Sound ballPop;
    private Sound victory;

    /**
     * Constructor method
     *
     * @param game Main class
     * @param manager Class responsible for playing sounds and music
     */
    public PlayScreen(PangGame game, AssetManager manager) {
        this.game = game;
        this.manager = manager;

        init();
    }

    /**
     * Initializer of the class
     */
    private void init() {
        cam = new OrthographicCamera();
        cam.setToOrtho(false, PangGame.V_WIDTH  / PangGame.PPM, PangGame.V_HEIGHT/ PangGame.PPM);

        port = new FitViewport(PangGame.V_WIDTH / PangGame.PPM, PangGame.V_HEIGHT / PangGame.PPM, cam);
        cam.position.set(port.getWorldWidth() / 2, port.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0f, -9.8f), true);
        world.setContactListener(new CollisionDetector());

        // Creates the boundaries of the screen game(Logic representation for collision detections)
        new Boundary(this, BoundaryType.FLOOR);
        new Boundary(this, BoundaryType.TOP);
        new Boundary(this, BoundaryType.RIGHT_WALL);
        new Boundary(this, BoundaryType.LEFT_WALL);

        balls = new Array<Ball>();
        balls.add(new Ball(this, null, MathUtils.randomSign()));

        renderer = new Box2DDebugRenderer();

        float playerPosY = PangGame.BOUNDARY_THICKNESS + PangGame.PLAYER_HEIGHT / 2;

        player1 = new Player(this, PangGame.POS_PLAYER1, playerPosY, 1);
        player2 = new Player(this, PangGame.POS_PLAYER2, playerPosY, 2);

        harpoons = new LinkedList<Harpoon>();

        shootSound = manager.get("gamesounds/GunClank.mp3");
        ballPop = manager.get("gamesounds/BubblePop.mp3");




        //animationManager.load(1, "L", 1);
    }

    private void update(float dt) {
        handleInput();
        world.step(1 / 60f,6, 2);

        spawnBalls();

        Iterator<Harpoon> harpoonIterator = harpoons.iterator();

        while (harpoonIterator.hasNext()){
            Harpoon harpoon = harpoonIterator.next();

            harpoon.update();

            if (harpoon.isDestroyed()){
                world.destroyBody(harpoon.getBody());
                harpoon.getOwner().shot();
                harpoonIterator.remove();

            }
        }

        Iterator<Ball> ballIterator = balls.iterator();

        while (ballIterator.hasNext()) {
            Ball ball = ballIterator.next();

            ball.update();

            if (ball.isDestroy() && ball.getSizeBall() != 4) {
                ballPop.play();
                balls.add(new Ball(this, ball, -1));
                balls.add(new Ball(this, ball, 1));

                world.destroyBody(ball.getBody());

                ballIterator.remove();
            } else if(ball.isDestroy() && ball.getSizeBall() == 4) {
                ballPop.play();
                world.destroyBody(ball.getBody());

                ballIterator.remove();
            }
        }

        player1.update(dt);
        player2.update(dt);

        if (player1.isDead()){
            game.setScreen(new GameOverScreen(game, manager, 2));
            dispose();
        } else if (player2.isDead()) {
            game.setScreen(new GameOverScreen(game, manager, 1));
        }


        cam.update();
    }

    /**
     * Is listening for a specific input by the player
     */
    private void handleInput() {
        handlePlayer1Input();
        handlePlayer2Input();
    }

    /**
     * Player1 listener
     */
    private void handlePlayer1Input() {
        if (Gdx.input.isKeyPressed(Input.Keys.Z) && player1.getBody().getPosition().x - (PangGame.PLAYER_WIDTH / 2 / PangGame.PPM) - (PangGame.BOUNDARY_THICKNESS / PangGame.PPM) > 0) {
            player1.getBody().setLinearVelocity(-PangGame.PLAYER_SPEED, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.X) && player1.getBody().getPosition().x + (PangGame.PLAYER_WIDTH / 2 / PangGame.PPM) + (PangGame.BOUNDARY_THICKNESS / PangGame.PPM) < PangGame.V_WIDTH / PangGame.PPM) {
            player1.getBody().setLinearVelocity(PangGame.PLAYER_SPEED, 0);
        } else {
            Vector2 p1Vel = player1.getBody().getLinearVelocity();
            if (p1Vel.x != 0 || p1Vel.y != 0) {
                setToSteady(player1);
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)) {
            if (!player1.getShot()) {
                harpoons.add(player1.shoot());
                shootSound.play();
            }

        }
    }

    /**
     * Player2 listener
     */
    private void handlePlayer2Input() {
        if (Gdx.input.isKeyPressed(Input.Keys.M) && player2.getBody().getPosition().x - (PangGame.PLAYER_WIDTH / 2 / PangGame.PPM) - (PangGame.BOUNDARY_THICKNESS / PangGame.PPM) > 0) {
            player2.getBody().setLinearVelocity(-PangGame.PLAYER_SPEED, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.COMMA) && player2.getBody().getPosition().x + (PangGame.PLAYER_WIDTH / 2 / PangGame.PPM) + (PangGame.BOUNDARY_THICKNESS / PangGame.PPM) < PangGame.V_WIDTH / PangGame.PPM) {
           player2.getBody().setLinearVelocity(PangGame.PLAYER_SPEED, 0);
        } else {
            Vector2 p2Vel = player2.getBody().getLinearVelocity();
            if (p2Vel.x != 0 || p2Vel.y != 0) {
               setToSteady(player2);
           }
       }
       if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {

           if (!player2.getShot()) {
               harpoons.add(player2.shoot());
               shootSound.play();
           }

       }
   }

    /**
     * Stops the player when there isn't any input
     *
     * @param player
     */
    private void setToSteady(Player player) {
        player.getBody().setLinearVelocity(0, 0);
    }

    /**
     * Create a new ball every ball spawn time seconds
     */
    private void spawnBalls() {
        if (System.nanoTime() > lastSpawn) {
            lastSpawn = System.nanoTime() + (1000000000 * PangGame.BALL_SPAWN_TIME);

            balls.add(new Ball(this, null, MathUtils.randomSign()));
        }
    }

    /**
     * Getter
     *
     * @return
     */
    public World getWorld() {
        return world;
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

        game.getBatch().end();

        // renderer.render(world, cam.combined);
    }

    @Override
    /**
     * @see Screen#resize(int, int)
     */
    public void resize(int width, int height) {

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
        background.dispose();
        renderer.dispose();
        world.dispose();
    }
}
