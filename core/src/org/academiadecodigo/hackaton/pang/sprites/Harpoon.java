package org.academiadecodigo.hackaton.pang.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import org.academiadecodigo.hackaton.pang.PangGame;
import org.academiadecodigo.hackaton.pang.screens.PlayScreen;

/**
 * Created by codecadet on 07/07/16.
 */
public class Harpoon extends Sprite {

    private World world;
    private Body body;
    private Fixture fixture;
    private boolean destroyed;

    // Who shoot the harpoon
    private Player owner;

    /**
     * Constructor method
     *
     * @param playScreen Game reference
     * @param x Position X axis
     * @param player Player who shot
     */
    public Harpoon(PlayScreen playScreen, float x, Player player) {
        super(new Texture("Harpoon/HarpoonStaticP1.png"));
        world = playScreen.getWorld();
        owner = player;

        this.setSize(PangGame.HARPOON_WIDTH / PangGame.PPM, PangGame.HARPOON_HEIGHT / PangGame.PPM);

        // Define the body definitions
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(x + PangGame.HARPOON_WIDTH / PangGame.PPM, (PangGame.BOUNDARY_THICKNESS - PangGame.HARPOON_HEIGHT / 2) / PangGame.PPM);

        body = world.createBody(bodyDef);

        // Sets the fixture(size, shape)
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((PangGame.HARPOON_WIDTH / 2) / PangGame.PPM, (PangGame.HARPOON_HEIGHT / 2) / PangGame.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.restitution = 1f;

        fixtureDef.filter.categoryBits = PangGame.HARPOON_BIT;

        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        move();
    }

    /**
     * Update the position according to body position
     * and checks when it collides with the top boundary of the game
     */
    public void update() {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        if (getY() > (PangGame.V_HEIGHT - PangGame.BOUNDARY_THICKNESS - PangGame.HARPOON_HEIGHT) / PangGame.PPM){
            destroy();
        }
    }

    /**
     * Set a constance movement
     */
    private void move() {
        body.setLinearVelocity(0, PangGame.HARPOON_SPEED);
    }

    /**
     * Getter
     *
     * @return
     */
    public boolean isDestroyed() {
        return destroyed;
    }

    /**
     * Getter
     *
     * @return
     */
    public Body getBody() {
        return body;
    }

    /**
     * Getter
     *
     * @return
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Setter
     */
    public void destroy() {
        destroyed = true;
    }
}
