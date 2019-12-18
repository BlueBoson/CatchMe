package com.example.catchme.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

class Character extends Sprite {

    static private final float HEIGHT_RATE = 0.25f;
    static private final float VELOCITY_RATE = 0.04f;
    static private final int FLASH_FRAME_NUM = 10;
    static private final int SEGMENT_NUM = 4;

    private float jumpHeight = 0;
    private float jumpVelocity = 0;
    private float jumpOrigin = 0;
    private boolean canJump = true;
    private boolean jumping = false;
    private int level = 0;

    Character(Bitmap bitmap){
        super(bitmap);
    }

    void adjust(Canvas canvas, GameView gameView) {
        jumpHeight = canvas.getHeight() * HEIGHT_RATE;
        jumpVelocity = jumpHeight * VELOCITY_RATE;
        centerTo(canvas.getWidth() / 2, 0);
        setY(gameView.getFloorY(getX()) - getHeight() - 1);
    }

    void jump() {
        if (!canJump) {
            return;
        }
        jumping = true;
        canJump = false;
        jumpOrigin = getY();
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
    void beforeDraw(Canvas canvas, Paint paint, GameView gameView) {
        super.beforeDraw(canvas, paint, gameView);
        if (jumping) {
            move(0, -jumpVelocity);
            if (getY() <= jumpOrigin - jumpHeight) {
                setY(jumpOrigin - jumpHeight);
                jumping = false;
            }
        } else {
            float oldY = getY();
            float floorY = gameView.getFloorY(getX() + getWidth() / 2);
            if (oldY + getHeight() / 2 <= floorY && oldY + getHeight() + jumpVelocity > floorY) {
                setY(floorY - getHeight());
                canJump = true;
            } else {
                move(0, jumpVelocity);
                canJump = false;
            }
        }
        if(getFrame() % FLASH_FRAME_NUM == 0){
            level++;
            if(level >= SEGMENT_NUM){
                level = 0;
            }
        }
    }
}
