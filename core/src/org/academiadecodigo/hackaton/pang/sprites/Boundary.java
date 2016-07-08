package org.academiadecodigo.hackaton.pang.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.academiadecodigo.hackaton.pang.PangGame;
import org.academiadecodigo.hackaton.pang.screens.PlayScreen;
import org.academiadecodigo.hackaton.pang.utilities.BoundaryType;

/**
 * Created by codecadet on 07/07/16.
 *
 * Class responsible to create boundaries to set
 * the limits of the world
 */
public class Boundary extends Sprite {

    // Manages all physics GameObjects
    private World world;

    // Receive impulses and forces
    private Body body;

    // Describes properties(size, shape) of an object
    private Fixture fixture;

    private BoundaryType boundaryType;

    /**
     * Constructor of a boundary
     * Set texture, size and
     * reference to the game world
     *
     * @param playScreen Game references
     * @param boundaryType Boundary type
     */
    public Boundary(PlayScreen playScreen, BoundaryType boundaryType) {
        this.boundaryType = boundaryType;

        world = playScreen.getWorld();

        defineBoundary();
    }

    /**
     * Define the boundary characteristics
     */
    private void defineBoundary() {
        body = setBodyDef();
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

        float[] position = definePosition(boundaryType);
        bodyDef.position.set(position[0], position[1]);

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
        shape.set(defineVertices(boundaryType));

        // For collision detection
        fdef.filter.categoryBits = PangGame.BOUNDARY_BIT;

        // Define material properties
        fdef.shape = shape;
        fdef.restitution = 0f;          // Bounciness
        fdef.friction = 0f;

        fixture = body.createFixture(fdef);
        fixture.setUserData(this);
    }

    /**
     * Sets the position of the boundary
     * according to his type
     *
     * @param boundaryType The boundary type
     * @return Array with the position of the boundary
     */
    private float[] definePosition(BoundaryType boundaryType) {
        float[] position = new float[2];

        switch (boundaryType) {
            case FLOOR:
                position[0] = PangGame.V_WIDTH / 2 / PangGame.PPM;
                position[1] = 0;
                break;

            case TOP:
                position[0] = PangGame.V_WIDTH / 2 / PangGame.PPM;
                position[1] = (PangGame.V_HEIGHT - PangGame.BOUNDARY_THICKNESS) / PangGame.PPM;
                break;

            case RIGHT_WALL:
                position[0] = PangGame.V_WIDTH / PangGame.PPM;
                position[1] = 0;
                break;

            case LEFT_WALL:
                position[0] = 0;
                position[1] = 0;
                break;

        }

        return position;
    }

    /**
     * Define the polygon shape
     *
     * @param boundaryType The boundary type
     * @return vertices of the boundary
     */
    private Vector2[] defineVertices(BoundaryType boundaryType) {
        Vector2[] vertice = new Vector2[4];

        switch (boundaryType) {
            case FLOOR:
            case TOP:
                vertice[0] = new Vector2(-PangGame.V_WIDTH / 2, PangGame.BOUNDARY_THICKNESS).scl(1 / PangGame.PPM);
                vertice[1] = new Vector2(-PangGame.V_WIDTH / 2, 0).scl(1 / PangGame.PPM);
                vertice[2] = new Vector2(PangGame.V_WIDTH / 2, PangGame.BOUNDARY_THICKNESS).scl(1 / PangGame.PPM);
                vertice[3] = new Vector2(PangGame.V_WIDTH / 2, 0).scl(1 / PangGame.PPM);
                break;

            case RIGHT_WALL:
            case LEFT_WALL:
                vertice[0] = new Vector2(-PangGame.BOUNDARY_THICKNESS, PangGame.V_HEIGHT).scl(1 / PangGame.PPM);
                vertice[1] = new Vector2(-PangGame.BOUNDARY_THICKNESS, 0).scl(1 / PangGame.PPM);
                vertice[2] = new Vector2(PangGame.BOUNDARY_THICKNESS, PangGame.V_HEIGHT).scl(1 / PangGame.PPM);
                vertice[3] = new Vector2(PangGame.BOUNDARY_THICKNESS, 0).scl(1 / PangGame.PPM);
                break;

        }

        return vertice;
    }
}
