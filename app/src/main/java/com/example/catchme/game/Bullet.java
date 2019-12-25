package com.example.catchme.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

class Bullet extends Sprite {

    static private final float COLLIDE_OFFSET_RATE = 0.5f;
    static private final float VELOCITY_RATE = 0.001f;

    Bullet(Bitmap bitmap) {
        super(bitmap);
        setCollideOffset(COLLIDE_OFFSET_RATE * getHeight());
    }

    void bondToCharacter(Character character) {
        moveTo(character.getX() + character.getWidth(),
                character.getY() + character.getHeight() / 2 - getHeight() / 2);
    }

    int getDamage() {
        return 1;
    }

    @Override
    void beforeDraw(Canvas canvas, Paint paint, GameView gameView) {
        super.beforeDraw(canvas, paint, gameView);
        move(VELOCITY_RATE * canvas.getWidth(), 0);
    }

    @Override
    void afterDraw(Canvas canvas, Paint paint, GameView gameView) {
        super.afterDraw(canvas, paint, gameView);
        if (getX() >= canvas.getWidth()) {
            destroy();
        }
    }
}
