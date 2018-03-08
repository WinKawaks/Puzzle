package cn.xxx.winkawaks.puzzle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import cn.xxx.winkawaks.puzzle.module.menu.MenuActivity;
import cn.xxx.winkawaks.puzzle.module.sound.BGMService;
import cn.xxx.winkawaks.puzzle.view.TitleTextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences mSharedPreferences = getSharedPreferences("WinKawaks", Context.MODE_PRIVATE);
        Boolean musicOn = mSharedPreferences.getBoolean("music", false);
        if (musicOn) {
            Intent intent = new Intent(this, BGMService.class);
            startService(intent);
        }

        TitleTextView titleTextView = (TitleTextView) findViewById(R.id.title);
        titleTextView.setText(getResources().getString(R.string.app_name), AnimationUtils.loadAnimation(this, R.anim.title_anim), 500);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        }, 3000);
    }
}
