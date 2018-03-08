package cn.xxx.winkawaks.puzzle;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import cn.xxx.winkawaks.puzzle.module.sound.BGMService;
import cn.xxx.winkawaks.puzzle.module.sound.SoundPlayer;

/**
 * Created by 54713 on 2017/11/13.
 */

public class MyApplication extends Application {
    public static int activityFront = 0;
    public static SoundPlayer soundPool;

    @Override
    public void onCreate() {
        super.onCreate();
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                activityFront++;
                SharedPreferences mSharedPreferences = getSharedPreferences("WinKawaks", Context.MODE_PRIVATE);
                Boolean musicOn = mSharedPreferences.getBoolean("music", false);
                if (activityFront > 0 && musicOn) {
                    Intent intent = new Intent(MyApplication.this, BGMService.class);
                    startService(intent);
                }
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                activityFront--;
                if (activityFront <= 0) {
                    Intent intent = new Intent(MyApplication.this, BGMService.class);
                    stopService(intent);
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
        SharedPreferences mSharedPreferences = getSharedPreferences("WinKawaks", Context.MODE_PRIVATE);
        Boolean soundOn = mSharedPreferences.getBoolean("sound", true);
        if (soundOn) {
            soundPool = new SoundPlayer();
            soundPool.init(this);
        }
    }

    public static SoundPlayer getSoundPool(Context context) {
        if (soundPool == null) {
            soundPool = new SoundPlayer();
            soundPool.init(context);
        }
        return soundPool;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        soundPool.unload();
    }
}
