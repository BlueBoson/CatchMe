package com.example.catchme.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

class Explosion extends LeftMovingSprite {

    static private final int SEGMENT_NUM = 14;
    static private final int FRAME_GAP = 2;
    private int level = 0;

    public Explosion(Bitmap bitmap){
        super(bitmap);
    }

    @Override
    float getWidth() {
        Bitmap bitmap = getBitmap();
        if(bitmap != null){
            return bitmap.getWidth() / SEGMENT_NUM;
        }
        return 0;
    }

    @Override
    Rect getBitmapSrcRec() {
        Rect rect = super.getBitmapSrcRec();
        int left = (int)(level * getWidth());
        rect.offsetTo(left, 0);
        return rect;
    }

    @Override
    void afterDraw(Canvas canvas, Paint paint, GameView gameView) {
        if(!isDestroyed()){
            if(getFrame() % FRAME_GAP == 0){
                level++;
                if(level >= SEGMENT_NUM){
                    destroy();
                }
            }
        }
    }
}
