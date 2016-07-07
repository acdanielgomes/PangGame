package org.academiadecodigo.hackaton.pang.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by codecadet on 07/07/16.
 */
public class Harpoon extends Sprite {

    private World world;

    private Body body;
    private PolygonShape shape;
    private Texture texture;
    private Fixture fixture;


    public Harpoon(int x) {

        world = new World(new Vector2(0, 0), true);
        texture = new Texture("harpoon.png");

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(x, 0);

        body = world.createBody(bodyDef);

        shape = new PolygonShape();
        shape.setAsBox(texture.getWidth()/ 2, texture.getHeight() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        fixture = body.createFixture(fixtureDef);

        move();
    }

    public void move() {
        body.applyLinearImpulse(0, 10, texture.getWidth() / 2, texture.getHeight() / 2, true);
    }

    public void dispose() {
        texture.dispose();
        shape.dispose();
        world.dispose();
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
}
