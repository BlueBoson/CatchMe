package com.example.catchme.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

class Background extends Sprite {

    static private float velocityRate =0;

    private float posRate = 0;

    Background(Bitmap bitmap) {
        super(bitmap);
    }

    static void setVelocityRate(float velocityRate) {
        Background.velocityRate = velocityRate;
    }

    static float getVelocityRate() {
        return velocityRate;
    }

    @Override
    void beforeDraw(Canvas canvas, Paint paint, GameView gameView) {
        super.beforeDraw(canvas, paint, gameView);
        posRate += velocityRate;
        if (posRate >= 1) {
            posRate -= 1;
        }
    }

    @Override
    void onDraw(Canvas canvas, Paint paint, GameView gameView) {
        float left = posRate * getWidth();
        float right = left + (float)canvas.getWidth() / (float)canvas.getHeight() * getHeight();
        if (right < getWidth()) {
            Rect srcRef = new Rect((int)left, 0, (int)right, (int)getHeight());
            RectF dstRecF = new RectF(0, 0, canvas.getWidth(), canvas.getHeight());
            canvas.drawBitmap(getBitmap(), srcRef, dstRecF, paint);
        } else {
            Rect srcRef = new Rect((int)left, 0, (int)getWidth(), (int)getHeight());
            float canvasRight = (getWidth() - left) / getHeight() * canvas.getHeight();
            RectF dstRecF = new RectF(0, 0, canvasRight, canvas.getHeight());
            canvas.drawBitmap(getBitmap(), srcRef, dstRecF, paint);
            srcRef = new Rect(0, 0, (int)(right - getWidth()), (int)getHeight());
            dstRecF = new RectF(canvasRight, 0, canvas.getWidth(), canvas.getHeight());
            canvas.drawBitmap(getBitmap(), srcRef, dstRecF, paint);
        }
    }
}
