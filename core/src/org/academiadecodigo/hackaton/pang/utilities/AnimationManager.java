package org.academiadecodigo.hackaton.pang.utilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by codecadet on 08/07/16.
 */
public class AnimationManager {

    private static Texture texture;
    private static Animation animation;

    public static void load(int a, String s, int b) {

        TextureRegion[] array;

        texture = new Texture("Player" + a + "/P" + a + s + ".png");

        switch (b) {

            case 1: //Player 1 left animation
                TextureRegion P1L1 = new TextureRegion(texture, 0, 0, 28, 31);
                TextureRegion P1L2 = new TextureRegion(texture, 28, 0, 28, 31);
                TextureRegion P1L3 = new TextureRegion(texture, 56, 0, 28, 31);
                TextureRegion P1L4 = new TextureRegion(texture, 84, 0, 28, 31);

                array = new TextureRegion[]{P1L1, P1L2, P1L3, P1L4};
                animation = new Animation(0.05f, array);
                animation.setPlayMode(Animation.PlayMode.NORMAL);

                break;

            case 2: //Player 1 right animation
                TextureRegion P1R1 = new TextureRegion(texture, 0, 0, 28, 31);
                TextureRegion P1R2 = new TextureRegion(texture, 28, 0, 28, 31);
                TextureRegion P1R3 = new TextureRegion(texture, 56, 0, 28, 31);
                TextureRegion P1R4 = new TextureRegion(texture, 84, 0, 28, 31);

                array = new TextureRegion[]{P1R1, P1R2, P1R3, P1R4};
                animation = new Animation(0.05f, array);
                animation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
                break;

            case 3: //Player 1 shoot animation
                TextureRegion P1S1 = new TextureRegion(texture, 0, 0, 28, 31);
                TextureRegion P1S2 = new TextureRegion(texture, 28, 0, 28, 31);

                array = new TextureRegion[]{P1S1, P1S2};
                animation = new Animation(0.05f, array);
                animation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
                break;

            case 4: //Player 2 left animation

                TextureRegion P2L1 = new TextureRegion(texture, 0, 0, 28, 31);
                TextureRegion P2L2 = new TextureRegion(texture, 28, 0, 28, 31);
                TextureRegion P2L3 = new TextureRegion(texture, 56, 0, 28, 31);
                TextureRegion P2L4 = new TextureRegion(texture, 84, 0, 28, 31);

                array = new TextureRegion[]{P2L1, P2L2, P2L3, P2L4};
                animation = new Animation(0.05f, array);
                animation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
                break;

            case 5: //Player 2 right animation

                TextureRegion P2R1 = new TextureRegion(texture, 0, 0, 28, 31);
                TextureRegion P2R2 = new TextureRegion(texture, 28, 0, 28, 31);
                TextureRegion P2R3 = new TextureRegion(texture, 56, 0, 28, 31);
                TextureRegion P2R4 = new TextureRegion(texture, 84, 0, 28, 31);

                array = new TextureRegion[]{P2R1, P2R2, P2R3, P2R4};
                animation = new Animation(0.05f, array);
                animation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
                break;

            case 6: //Player 2 shoot animation

                TextureRegion P2S1 = new TextureRegion(texture, 0, 0, 28, 31);
                TextureRegion P2S2 = new TextureRegion(texture, 28, 0, 28, 31);

                array = new TextureRegion[]{P2S1, P2S2};
                animation = new Animation(0.05f, array);
                animation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);


        }

    }

    public static void dispose() {
        texture.dispose();
    }
}
