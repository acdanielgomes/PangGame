package org.academiadecodigo.hackaton.pang.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.academiadecodigo.hackaton.pang.PangGame;
import org.academiadecodigo.hackaton.pang.screens.PlayScreen;

/**
 * Created by codecadet on 07/07/16.
 */
public class Ball extends Sprite {

    // Manages all physics GameObjects
    private World world;

    // Receive impulses and forces
    private Body body;

    // Describes properties(size, shape) of an object
    private Fixture fixture;

    private Ball previousBall;

    private int sizeBall;

    /**
     * Constructor of a ball
     * Set texture, size
     * and reference to the game world
     *
     * @param playScreen Game references
     */
    public Ball(PlayScreen playScreen, Ball previousBall) {
        super(new Texture("Balloons/LStar.png"));

        world = playScreen.getWorld();

        if (previousBall == null) {
            sizeBall = 1;
        } else {
            sizeBall = previousBall.getSizeBall() + 1;
        }

        float ballSize = (PangGame.BALL_RADIUS * 2 / PangGame.PPM) / sizeBall;
        setSize(ballSize, ballSize);

        defineBall();
    }

    /**
     * Update properties of the ball
     *
     * @param delta Time since the last update
     */
    public void update(float delta) {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }

    /**
     * Define the ball characteristics
     */
    private void defineBall() {
        body = setBodyDef();
        body.applyLinearImpulse(new Vector2(10f * MathUtils.randomSign(), 0), body.getWorldCenter(), true);
        setFixtureDef();
    }

    /**
     * Creates a body with a set of definitions
     * Sets the position and the body type
     *
     * @return
     */
    private Body setBodyDef() {
        // Holds all the data needed to construct a rigid body
        BodyDef bodyDef = new BodyDef();

        bodyDef.position.set(PangGame.V_WIDTH / 2 / PangGame.PPM, PangGame.V_HEIGHT / 2 / PangGame.PPM);

        // Velocity determined by forces
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        return world.createBody(bodyDef);
    }

    /**
     * Sets the fixture(size, shape)
     * sets a circular shape for the collide
     * defines de material properties
     */
    private void setFixtureDef() {
        FixtureDef fdef = new FixtureDef();

        CircleShape shape = new CircleShape();
        shape.setRadius(PangGame.BALL_RADIUS / PangGame.PPM / sizeBall);

        // For collision detection
//        fdef.filter.categoryBits = PangGame.BALL_BIT;                                           // Define who is it
//        fdef.filter.maskBits = PangGame.BLOCK_BIT | PangGame.EDGE_BIT | PangGame.PADDLE_BIT;    // Define with whom can collide

        // Define material properties
        fdef.shape = shape;
        fdef.restitution = 1f;          // Bounciness of the ball
        fdef.friction = 0f;

        fixture = body.createFixture(fdef);
        fixture.setUserData(this);
    }

    public int getSizeBall() {
        return sizeBall;
    }

    /**
     * Getter
     *
     * @return
     */
    public Body getBody() {
        return body;
    }
}
