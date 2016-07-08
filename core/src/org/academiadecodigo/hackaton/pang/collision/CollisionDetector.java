package org.academiadecodigo.hackaton.pang.collision;

import com.badlogic.gdx.physics.box2d.*;
import org.academiadecodigo.hackaton.pang.PangGame;
import org.academiadecodigo.hackaton.pang.sprites.Ball;
import org.academiadecodigo.hackaton.pang.sprites.Harpoon;
import org.academiadecodigo.hackaton.pang.sprites.Player;

/**
 * Created by codecadet on 07/07/16.
 */
public class CollisionDetector implements ContactListener {
    @Override
    public void beginContact(Contact contact) {

        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case PangGame.BALL_BIT | PangGame.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == PangGame.BALL_BIT) {
                    ((Player) fixB.getUserData()).onHit();
                } else {
                    ((Player) fixA.getUserData()).onHit();
                }
                break;

            case PangGame.BALL_BIT | PangGame.HARPOON_BIT:
                if (fixA.getFilterData().categoryBits == PangGame.BALL_BIT) {
                    ((Ball) fixA.getUserData()).destroy();
                    ((Harpoon) fixB.getUserData()).destroy();
                } else {
                    ((Ball) fixB.getUserData()).destroy();
                    ((Harpoon) fixA.getUserData()).destroy();
                }
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
