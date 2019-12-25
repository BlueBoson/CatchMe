package com.example.catchme.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

class GameObject extends LeftMovingSprite {

    static private final int MOVE_RATE = 4;
    static private final float VELOCITY_RATE = 0.01f;

    private boolean horizontal = false;
    private boolean vertical = false;
    private int score = 0;
    private int hp = 0;
    private float xOffset = 0;
    private float yOffset = 0;
    private boolean xReverse = false;
    private boolean yReverse = false;

    GameObject(Bitmap bitmap) {
        super(bitmap);
    }

    GameObject(Bitmap bitmap, boolean horizontal, boolean vertical, int score) {
        super(bitmap);
        this.horizontal = horizontal;
        this.vertical = vertical;
        this.score = score;
    }

    GameObject(Bitmap bitmap, boolean horizontal, boolean vertical, int score, int hp) {
        super(bitmap);
        this.horizontal = horizontal;
        this.vertical = vertical;
        this.score = score;
        this.hp = hp;
    }

    void bondToFloor(Floor floor) {
        setX(floor.getX() + floor.getWidth() / 2);
        setY(floor.getY() - getHeight());
    }

    int getScore() {
        return score;
    }

    void decreaseHp(int damage) {
        if (hp > 0) {
            hp -= damage;
        }
    }

    int getHp() {
        return hp;
    }

    @Override
    void beforeDraw(Canvas canvas, Paint paint, GameView gameView) {
        super.beforeDraw(canvas, paint, gameView);
        if (horizontal) {
            float max = MOVE_RATE * getWidth();
            float delta = VELOCITY_RATE * max;
            if (xReverse) {
                delta = delta + canvas.getWidth() * getVelocityRate() / 2;
                xOffset += delta;
                move(delta, 0);
                if (xOffset >= 0) {
                    xReverse = false;
                }
            } else {
                xOffset -= delta;
                move(-delta, 0);
                if (xOffset <= -max) {
                    xReverse = true;
                }
            }
        }
        if (vertical) {
            float max = MOVE_RATE * getHeight();
            float delta = VELOCITY_RATE * max;
            if (yReverse) {
                yOffset += delta;
                move(0, delta);
                if (yOffset >= 0) {
                    yReverse = false;
                }
            } else {
                yOffset -= delta;
                move(0, -delta);
                if (yOffset <= -max) {
                    yReverse = true;
                }
            }
        }
    }

    @Override
    void afterDraw(Canvas canvas, Paint paint, GameView gameView) {
        super.afterDraw(canvas, paint, gameView);
        if (getCollidePointWithOther(gameView.getCharacter()) != null) {
            collideEvent(canvas, paint, gameView);
        }
    }

    void collideEvent(Canvas canvas, Paint paint, GameView gameView) {

    }
}
