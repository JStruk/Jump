package com.mygdx.jump;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Justin on 2015-04-14.
 */
public class Input implements KeyListener {
    public static boolean isLeft, isRight;
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (Gdx.input.isKeyPressed(Keys.UP)) {
            //Make player move up...
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            //Make player move down...
        }
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            System.out.println("leftpressed");
            isLeft = true;
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            isRight = true;
        }
    }

    public static boolean MoveLeft() {
        return isLeft;
    }

    public static boolean MoveRight() {
        return isRight;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
