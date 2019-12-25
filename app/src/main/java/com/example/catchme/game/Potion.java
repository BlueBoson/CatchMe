package com.example.catchme.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

class Potion extends GameObject {
    static private final float COLLIDE_OFFSET_RATE = 0.2f;

    Potion(Bitmap bitmap) {
        super(bitmap);
        setCollideOffset(COLLIDE_OFFSET_RATE * getHeight());
    }

    Potion(Bitmap bitmap, boolean horizontal, boolean vertical, int score) {
        super(bitmap, horizontal, vertical, score);
        setCollideOffset(COLLIDE_OFFSET_RATE * getHeight());
    }

    @Override
    void collideEvent(Canvas canvas, Paint paint, GameView gameView) {
        super.collideEvent(canvas, paint, gameView);
        destroy();
        gameView.addScore(getScore(), true);
    }
}
