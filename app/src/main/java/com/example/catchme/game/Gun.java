package com.example.catchme.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

class Gun extends Potion {

    static private int SCORE = 0;

    Gun(Bitmap bitmap) {
        super(bitmap);
    }

    Gun(Bitmap bitmap, boolean horizontal, boolean vertical) {
        super(bitmap, horizontal, vertical, SCORE);
    }

    @Override
    void collideEvent(Canvas canvas, Paint paint, GameView gameView) {
        super.collideEvent(canvas, paint, gameView);
        gameView.getCharacter().shoot();
    }
}
