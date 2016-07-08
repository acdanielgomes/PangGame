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
public class Harpoon extends Sprite {

    private World world;

    private Body body;
    private PolygonShape shape;
    private Fixture fixture;

    public Harpoon(PlayScreen playScreen, float x) {
        super(new Texture("Harpoon/HarpoonStaticP1.png"));
        world = playScreen.getWorld();

        this.setSize(PangGame.HARPOON_WIDTH / PangGame.PPM, PangGame.HARPOON_HEIGHT / PangGame.PPM);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(x + PangGame.HARPOON_WIDTH / PangGame.PPM, (PangGame.BOUNDARY_THICKNESS - PangGame.HARPOON_HEIGHT / 2) / PangGame.PPM);

        body = world.createBody(bodyDef);

        shape = new PolygonShape();
        shape.setAsBox((PangGame.HARPOON_WIDTH / 2) / PangGame.PPM, (PangGame.HARPOON_HEIGHT / 2) / PangGame.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        fixture = body.createFixture(fixtureDef);

        move();
    }

    public void update() {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }

    private void move() {
        body.setLinearVelocity(0 , PangGame.HARPOON_SPEED);
    }

    public void dispose() {
        getTexture().dispose();
        shape.dispose();

    }
}
