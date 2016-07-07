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
public class Boundary extends Sprite {

    // Manages all physics GameObjects
    private World world;

    // Receive impulses and forces
    private Body body;

    // Describes properties(size, shape) of an object
    private Fixture fixture;

    /**
     * Constructor of a boundary
     * Set texture, size and
     * reference to the game world
     *
     * @param playScreen Game references
     * @param posX
     * @param posY
     * @param height
     * @param width
     */
    public Boundary(PlayScreen playScreen, int posX, int posY, float width, float height) {
        super(new Texture("BOUNDARY_URL"));

        world = playScreen.getWorld();

        setSize(width, height);

        defineBoundary(posX, posY);
    }

    /**
     * Define the boundary characteristics
     *
     * @param posX
     * @param posY
     */
    private void defineBoundary(int posX, int posY) {
        body = setBodyDef(posX, posY);
        setFixtureDef();
    }

    /**
     * Creates a body with a set of definitions
     * Sets the position and the body type
     *
     * @param posX
     * @param posY
     * @return
     */
    private Body setBodyDef(int posX, int posY) {
        // Holds all the data needed to construct a rigid body
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(posX / PangGame.PPM, posY / PangGame.PPM);

        // Velocity determined by forces
        bodyDef.type = BodyDef.BodyType.StaticBody;
        return world.createBody(bodyDef);
    }

    /**
     * Sets the fixture(size, shape)
     * sets a circular shape for the collide
     * defines de material properties
     */
    private void setFixtureDef() {
        FixtureDef fdef = new FixtureDef();

        PolygonShape shape = new PolygonShape();
        shape.setRadius(PangGame.BALL_RADIUS / PangGame.PPM);
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-PangGame.V_WIDTH / 2, 10).scl(1 / PangGame.PPM);
        vertice[1] = new Vector2(-PangGame.V_WIDTH / 2, 0).scl(1 / PangGame.PPM);
        vertice[2] = new Vector2(PangGame.V_WIDTH / 2, 10).scl(1 / PangGame.PPM);
        vertice[3] = new Vector2(PangGame.V_WIDTH / 2, 0).scl(1 / PangGame.PPM);
        shape.set(vertice);

        // For collision detection
//        fdef.filter.categoryBits = PangGame.BOUNDARY_BIT;                                          // Define who is it
//        fdef.filter.maskBits = PangGame.BLOCK_BIT | PangGame.EDGE_BIT | PangGame.PADDLE_BIT;    // Define with whom can collide

        // Define material properties
        fdef.shape = shape;
        fdef.restitution = 0f;          // Bounciness of the material
        fdef.friction = 0f;

        fixture = body.createFixture(fdef);
        fixture.setUserData(this);
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
