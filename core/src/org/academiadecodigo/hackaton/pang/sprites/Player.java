package org.academiadecodigo.hackaton.pang.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.academiadecodigo.hackaton.pang.PangGame;
import org.academiadecodigo.hackaton.pang.screens.PlayScreen;

/**
 * Created by codecadet on 07/07/16.
 */
public class Player extends Sprite {

    /* Manages all physics GameObjects */
    public World world;

    /* Receive impulses and forces */
    private Body b2Body;

    /* Describes properties (size and shape) of an object */
    public Fixture fixture;

    /**
     * Constructor of the Player
     * Set texture, size and reference to the game world
     *
     * @param screen Game references
     * @param x Position X axis
     * @param y Position Y axis
     */
    public Player(PlayScreen screen, float x, float y) {

        super(new Texture("badlogic.jpg")); // TODO: 07/07/16 player image
        this.world = screen.getWorld();
        this.setSize(PangGame.PLAYER_WIDTH / PangGame.PPM, PangGame.PLAYER_HEIGHT / PangGame.PPM);

        definePlayer(x, y);
    }

    /**
     * Update properties of the Player
     *
     * @param dt Time since the last update
     */
    public void update(float dt) {

        setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
    }

    /**
     * Define the Player characteristics
     *
     * Creates a body with a set of definitions
     * Set the position and the body type
     *
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

        //fixtureDef.filter.categoryBits = PangGame.PLAYER_BIT;

        fixtureDef.shape = shape;

        // TODO: 07/07/16 check values
        fixtureDef.restitution = 0f;
        fixtureDef.friction = 0f;
        fixture = b2Body.createFixture(fixtureDef);
        fixture.setUserData(this);

    }

    /**
     * Getter for Body
     *
     * @return body
     */
    public Body getBody() {
        return b2Body;
    }

}
