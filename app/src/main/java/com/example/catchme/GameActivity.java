package com.example.catchme;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.catchme.game.GameView;

import java.util.HashMap;
import java.util.Map;

public class GameActivity extends AppCompatActivity {

    public enum Sprites {
        CHARACTER,
        FLOOR,
        BACKGROUND,
        PAUSE,
        MONSTER,
        COIN,
        GUN,
        BULLET,
        EXPLOSION,
    }

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);
        gameView = (GameView)findViewById(R.id.gameView);
        Map<Sprites, Bitmap> bitmaps = new HashMap<Sprites, Bitmap>() {{
            put(Sprites.CHARACTER, BitmapFactory.decodeResource(getResources(), R.drawable.character));
            put(Sprites.FLOOR, BitmapFactory.decodeResource(getResources(), R.drawable.floor));
            put(Sprites.BACKGROUND, BitmapFactory.decodeResource(getResources(), R.drawable.background));
            put(Sprites.PAUSE, BitmapFactory.decodeResource(getResources(), R.drawable.pause));
            put(Sprites.MONSTER, BitmapFactory.decodeResource(getResources(), R.drawable.monster));
            put(Sprites.COIN, BitmapFactory.decodeResource(getResources(), R.drawable.coin));
            put(Sprites.GUN, BitmapFactory.decodeResource(getResources(), R.drawable.gun));
            put(Sprites.BULLET, BitmapFactory.decodeResource(getResources(), R.drawable.bullet));
            put(Sprites.EXPLOSION, BitmapFactory.decodeResource(getResources(), R.drawable.explosion));

        }};
        gameView.start(bitmaps);
    }
}
