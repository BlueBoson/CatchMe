package com.example.catchme.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

class Monster extends GameObject {

    static private final float COLLIDE_OFFSET_RATE = -0.2f;
    static private int SCORE = 10;
    static private int HP = 3;

    Monster(Bitmap bitmap) {
        super(bitmap);
        setCollideOffset(COLLIDE_OFFSET_RATE * getHeight());
    }

    Monster(Bitmap bitmap, boolean horizontal, boolean vertical) {
        super(bitmap, horizontal, vertical, SCORE, HP);
        setCollideOffset(COLLIDE_OFFSET_RATE * getHeight());
    }

    @Override
    void afterDraw(Canvas canvas, Paint paint, GameView gameView) {
        super.afterDraw(canvas, paint, gameView);
        if (getX() < canvas.getWidth()) {
            for (Bullet bullet: gameView.getBullets()) {
                if (getCollidePointWithOther(bullet) != null) {
                    bullet.destroy();
                    decreaseHp(bullet.getDamage());
                }
            }
        }
        if (getHp() <= 0) {
            destroy();
            gameView.explode(this);
            gameView.addScore(getScore(), false);
        }
    }

    @Override
    void collideEvent(Canvas canvas, Paint paint, GameView gameView) {
        super.collideEvent(canvas, paint, gameView);
        gameView.gameOver();
    }
}
