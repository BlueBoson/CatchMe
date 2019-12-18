package com.example.catchme.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;

class Sprite {
    private float x = 0;
    private float y = 0;
    private float collideOffset = 0;
    private Bitmap bitmap;
    private boolean destroyed = false;
    private int frame = 0;

    Sprite(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    Bitmap getBitmap(){
        return bitmap;
    }
    
    void setX(float x){
        this.x = x;
    }

    float getX(){
        return x;
    }

    void setY(float y){
        this.y = y;
    }

    float getY(){
        return y;
    }

    float getWidth(){
        if(bitmap != null){
            return bitmap.getWidth();
        }
        return 0;
    }

    float getHeight(){
        if(bitmap != null){
            return bitmap.getHeight();
        }
        return 0;
    }

    void move(float offsetX, float offsetY){
        x += offsetX;
        y += offsetY;
    }

    void moveTo(float x, float y){
        this.x = x;
        this.y = y;
    }

    void centerTo(float centerX, float centerY){
        float w = getWidth();
        float h = getHeight();
        x = centerX - w / 2;
        y = centerY - h / 2;
    }

    RectF getRectF(){
        float left = x;
        float top = y;
        float right = left + getWidth();
        float bottom = top + getHeight();
        RectF rectF = new RectF(left, top, right, bottom);
        return rectF;
    }

    Rect getBitmapSrcRec(){
        Rect rect = new Rect();
        rect.left = 0;
        rect.top = 0;
        rect.right = (int)getWidth();
        rect.bottom = (int)getHeight();
        return rect;
    }

    RectF getCollideRectF(){
        RectF rectF = getRectF();
        rectF.left -= collideOffset;
        rectF.right += collideOffset;
        rectF.top -= collideOffset;
        rectF.bottom += collideOffset;
        return rectF;
    }

    Point getCollidePointWithOther(Sprite s){
        Point p = null;
        RectF rectF1 = getCollideRectF();
        RectF rectF2 = s.getCollideRectF();
        RectF rectF = new RectF();
        boolean isIntersect = rectF.setIntersect(rectF1, rectF2);
        if(isIntersect){
            p = new Point(Math.round(rectF.centerX()), Math.round(rectF.centerY()));
        }
        return p;
    }

    void draw(Canvas canvas, Paint paint, GameView gameView){
        frame++;
        beforeDraw(canvas, paint, gameView);
        onDraw(canvas, paint, gameView);
        afterDraw(canvas, paint, gameView);
    }

    void beforeDraw(Canvas canvas, Paint paint, GameView gameView){}

    void onDraw(Canvas canvas, Paint paint, GameView gameView){
        if(!destroyed && this.bitmap != null){
            Rect srcRef = getBitmapSrcRec();
            RectF dstRecF = getRectF();
            canvas.drawBitmap(bitmap, srcRef, dstRecF, paint);
        }
    }

    void afterDraw(Canvas canvas, Paint paint, GameView gameView){}

    void destroy(){
        bitmap = null;
        destroyed = true;
    }

    boolean isDestroyed(){
        return destroyed;
    }

    int getFrame(){
        return frame;
    }

}
