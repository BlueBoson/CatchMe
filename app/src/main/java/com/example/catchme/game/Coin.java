package com.example.catchme.game;

import android.graphics.Bitmap;

class Coin extends Potion {

    static private int SCORE = 10;

    Coin(Bitmap bitmap) {
        super(bitmap);
    }

    Coin(Bitmap bitmap, boolean horizontal, boolean vertical) {
        super(bitmap, horizontal, vertical, SCORE);
    }
}
