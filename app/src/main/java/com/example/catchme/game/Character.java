package com.example.catchme.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

class Character extends Sprite {

    static private final float COLLIDE_OFFSET_RATE = -0.3f;
    static private final float HEIGHT_RATE = 0.2f;
    static private final float JUMP_VELOCITY_RATE = 0.05f;
    static private final float FALL_VELOCITY_RATE = 0.06f;
    static private final int FLASH_FRAME_NUM = 10;
    static private final int SEGMENT_NUM = 4;
    static private final int SHOOT_FRAME = 1000;
    static private final int SHOOT_GAP = 20;

    private float jumpHeight = 0;
    private float jumpVelocity = 0;
    private float fallVelocity = 0;
    private float jumpOrigin = 0;
    private boolean canJump = true;
    private boolean jumping = false;
    private int level = 0;
    private int shoots = 0;

    Character(Bitmap bitmap){
        super(bitmap);
        setCollideOffset(COLLIDE_OFFSET_RATE * getHeight());
    }

    void adjust(Canvas canvas, GameView gameView) {
        jumpHeight = canvas.getHeight() * HEIGHT_RATE;
        jumpVelocity = jumpHeight * JUMP_VELOCITY_RATE;
        fallVelocity = jumpHeight * FALL_VELOCITY_RATE;
        centerTo(canvas.getWidth() / 2, 0);
        setY(gameView.getCenterFloorY(getX()) - getHeight() - 1);
    }

    void jump() {
        if (!canJump) {
            return;
        }
        jumping = true;
        canJump = false;
        jumpOrigin = getY();
    }

    void shoot() {
        shoots = SHOOT_FRAME;
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
            float floorY = gameView.getCenterFloorY(getX() + getWidth() / 2);
            if (oldY + getHeight() / 2 <= floorY && oldY + getHeight() + fallVelocity > floorY) {
                setY(floorY - getHeight());
                canJump = true;
            } else {
                move(0, fallVelocity);
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

    @Override
    void afterDraw(Canvas canvas, Paint paint, GameView gameView) {
        super.afterDraw(canvas, paint, gameView);
        if (shoots > 0) {
            shoots -= 1;
            if (shoots % SHOOT_GAP == 0) {
                gameView.shoot();
            }
        }
    }
}
