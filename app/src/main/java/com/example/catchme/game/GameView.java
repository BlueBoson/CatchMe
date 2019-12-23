package com.example.catchme.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.catchme.GameActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class GameView extends View {

    static private final int GROUND_LEVEL_NUM = 5;
    static private final float GAP_RATE = 0.05f;
    static private final int MIN_SEGMENT_NUM = 2;
    static private final int MAX_SEGMENT_NUM = 5;
    static private final float BG_VELOCITY_RATE = 0.002f;
    static private final float OBJECT_VELOCITY_RATE = 0.005f;

    private Character character;
    private Background background;
    private List<Floor> floors;
    Map<GameActivity.Sprites, Bitmap> bitmaps;
    private long frame = 0;
    private boolean clicked = false;
    private Paint paint;

    public GameView(Context context) {
        super(context);
        init();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        floors = new ArrayList<>();
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
    }

    float floorLevelY(Canvas canvas, int level) {
        return canvas.getHeight() / (GROUND_LEVEL_NUM + 2) * (GROUND_LEVEL_NUM  - level);
    }

    Floor randomRightFloor(Canvas canvas, Floor rightFloor) {
        Random r = new Random();
        int segments = r.nextInt(MAX_SEGMENT_NUM - MIN_SEGMENT_NUM + 1)
                + MIN_SEGMENT_NUM;
        Floor floor = new Floor(bitmaps.get(GameActivity.Sprites.FLOOR), segments);
        int level = 0;
        int rightLevel = rightFloor.getLevel();
        if (rightLevel == 0 || rightLevel < GROUND_LEVEL_NUM - 1 && r.nextInt(2) > 0) {
            level = rightLevel + 1;
        } else {
            level = r.nextInt(rightLevel);
        }
        floor.centerTo(0, floorLevelY(canvas, level));
        floor.setLevel(level);
        floor.setX(rightFloor.getX() + rightFloor.getWidth() + canvas.getWidth() * GAP_RATE);
        return floor;
    }

    float getFloorY(float x) {
        float y = 0;
        for (Floor floor: floors) {
            if (floor.getX() <= x && floor.getX() + floor.getWidth() >= x) {
                y = floor.getY();
            }
        }
        return y;
    }

    public void start(Map<GameActivity.Sprites, Bitmap> bitmaps) {
        this.bitmaps = bitmaps;
        postInvalidate();
    }

    void initGame(Canvas canvas) {
        Floor firstFloor = new Floor(bitmaps.get(GameActivity.Sprites.FLOOR), MAX_SEGMENT_NUM);
        firstFloor.centerTo(canvas.getWidth() / 2, floorLevelY(canvas, 0));
        firstFloor.setLevel(0);
        floors.add(firstFloor);
        character = new Character(bitmaps.get(GameActivity.Sprites.CHARACTER));
        character.adjust(canvas, this);
        background = new Background(bitmaps.get(GameActivity.Sprites.BACKGROUND));
        Background.setVelocityRate(BG_VELOCITY_RATE);
        LeftMovingSprite.setVelocityRate(OBJECT_VELOCITY_RATE);
    }

    void generateFloorIfCan(Canvas canvas) {
        Floor rightFloor = floors.get(floors.size() - 1);
        if (rightFloor.getX() < canvas.getWidth()) {
            floors.add(randomRightFloor(canvas, rightFloor));
        }
    }

    void deleteDestroyed() {
        Iterator<Floor> iterator = floors.iterator();
        while (iterator.hasNext()) {
            Floor floor = iterator.next();
            if (floor.isDestroyed()) {
                iterator.remove();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (frame == 0) {
            initGame(canvas);
        }
        if (clicked) {
            character.jump();
            clicked = false;
        }
        frame++;
        deleteDestroyed();
        generateFloorIfCan(canvas);
        background.draw(canvas, paint, this);
        character.draw(canvas, paint, this);
        for (Floor floor: floors) {
            floor.draw(canvas, paint, this);
        }
        postInvalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchType = event.getAction();
        if (touchType == MotionEvent.ACTION_DOWN) {
            clicked = true;
        }
        return true;
    }
}
