package org.academiadecodigo.hackaton.pang.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import org.academiadecodigo.hackaton.pang.PangGame;
import org.academiadecodigo.hackaton.pang.screens.PlayScreen;

import java.util.List;

/**
 * Created by codecadet on 07/07/16.
 */
public class Player extends Sprite {

    public enum State {
        STANDING,
        RUNNING,
        SHOOTING
    }

    public State currentState;
    public State previousState;

    private boolean isShooting;


    /* Manages all physics GameObjects */
    public World world;

    /* Receive impulses and forces */
    private PlayScreen playScreen;

    /* Receive impulses and forces */
    private Body b2Body;

    /* Describes properties (size and shape) of an object */
    public Fixture fixture;

    private boolean isDead;
    private boolean shot;

    private TextureRegion playerStand;
    private Animation playerShoot;
    private Animation playerRun;

    private boolean runningRight;
    private float stateTimer;
    private Texture leftAnimTexture;
    private Texture playerStandTexture;
    private Texture playerShootingTexture;

    /**
     * Constructor of the Player
     * Set texture, size and reference to the game world
     *
     * @param screen Game references
     * @param x      Position X axis
     * @param y      Position Y axis
     */
    public Player(PlayScreen screen, float x, float y, int playerNumber) {

        //super(new Texture("Player" + playerNumber + "/P" + playerNumber + "Still.png"));
        //leftAnimTexture = new Texture("Player" + playerNumber + "/P" + playerNumber + "R.png");

        playScreen = screen;
        this.world = screen.getWorld();
        this.setSize(PangGame.PLAYER_WIDTH / PangGame.PPM, PangGame.PLAYER_HEIGHT / PangGame.PPM);

        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> runFrames = new Array<TextureRegion>();
        runFrames.add(new TextureRegion(new Texture("Player/P" + playerNumber + "MoveR1.png")));
        runFrames.add(new TextureRegion(new Texture("Player/P" + playerNumber + "MoveR2.png")));
        runFrames.add(new TextureRegion(new Texture("Player/P" + playerNumber + "MoveR3.png")));
        runFrames.add(new TextureRegion(new Texture("Player/P" + playerNumber + "MoveR4.png")));
        playerRun = new Animation(0.1f, runFrames);


        Array<TextureRegion> shootFrames = new Array<TextureRegion>();
        shootFrames.add(new TextureRegion(new Texture("Player/P" + playerNumber + "shoot1.png")));
        shootFrames.add(new TextureRegion(new Texture("Player/P" + playerNumber + "shoot2.png")));
        playerShoot = new Animation(0.1f, shootFrames);

        playerStand = new TextureRegion(new Texture("Player/P" + playerNumber + "Still.png"));
        definePlayer(x, y);
    }

    /**
     * Update properties of the Player
     *
     * @param dt Time since the last update
     */
    public void update(float dt) {

        setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region;

        switch (currentState) {
            case RUNNING:
                region = playerRun.getKeyFrame(stateTimer, true);
                break;
            case SHOOTING:
                region = playerShoot.getKeyFrame(stateTimer, true);
                break;
            case STANDING:
            default:
                region = playerStand;
                break;
        }

        if ((b2Body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((b2Body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }


    public State getState() {
        if (b2Body.getLinearVelocity().x != 0) {
            isShooting = false;
            return State.RUNNING;
        } else if (isShooting) {
            return State.SHOOTING;
        } else {
            isShooting = false;
            return State.STANDING;
        }
    }

    /**
     * Define the Player characteristics
     * <p>
     * Creates a body with a set of definitions
     * Set the position and the body type
     * <p>
     * Sets the fixture (size and shape)
     * Defines the material properties
     *
     * @param x
     * @param y
     */
    public void definePlayer(float x, float y) {

        BodyDef bDef = new BodyDef();

        bDef.position.set(x / PangGame.PPM, y / PangGame.PPM);
        //bDef.position.set(PangGame.V_WIDTH / 2 / PangGame.PPM, PangGame.V_HEIGHT / 2 / PangGame.PPM);
        bDef.type = BodyDef.BodyType.KinematicBody;
        b2Body = world.createBody(bDef);


        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-PangGame.PLAYER_WIDTH / 2, PangGame.PLAYER_HEIGHT / 2).scl(1 / PangGame.PPM);
        vertice[1] = new Vector2(-PangGame.PLAYER_WIDTH / 2, -PangGame.PLAYER_HEIGHT / 2).scl(1 / PangGame.PPM);
        vertice[2] = new Vector2(PangGame.PLAYER_WIDTH / 2, -PangGame.PLAYER_HEIGHT / 2).scl(1 / PangGame.PPM);
        vertice[3] = new Vector2(PangGame.PLAYER_WIDTH / 2, PangGame.PLAYER_HEIGHT / 2).scl(1 / PangGame.PPM);

        shape.set(vertice);

        fixtureDef.filter.categoryBits = PangGame.PLAYER_BIT;

        fixtureDef.shape = shape;

        // TODO: 07/07/16 check values
        fixtureDef.restitution = 0f;
        fixtureDef.friction = 0f;
        fixture = b2Body.createFixture(fixtureDef);
        fixture.setUserData(this);

    }

    public void onHit() {
        isDead = true;
    }

    /**
     * Getter for boolean isDead
     *
     * @return if the player is dead
     */
    public boolean isDead() {
        return isDead;
    }

    /**
     * Getter for Body
     *
     * @return body
     */
    public Body getBody() {
        return b2Body;
    }

    public void shot() {
        shot = false;
    }

    public boolean getShot() {
        return shot;
    }

    public Harpoon shoot() {
        shot = true;
        isShooting = true;
        return new Harpoon(playScreen, getX(), this);
    }
}