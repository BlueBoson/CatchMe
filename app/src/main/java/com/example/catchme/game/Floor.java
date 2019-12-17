package com.example.catchme.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

class Floor extends LeftMovingSprite {

    static private final int SEGMENT_NUM = 5;

    private int segment = 0;
    private int level = 0;

    Floor(Bitmap bitmap,  int segment){
        super(bitmap);
        if (segment > SEGMENT_NUM) {
            segment = SEGMENT_NUM;
        }
        this.segment = segment;
    }

    int getLevel() {
        return level;
    }

    void setLevel(int level) {
        this.level = level;
    }

    @Override
    float getWidth() {
        Bitmap bitmap = getBitmap();
        if(bitmap != null){
            return bitmap.getWidth() * segment / SEGMENT_NUM;
        }
        return 0;
    }

}
