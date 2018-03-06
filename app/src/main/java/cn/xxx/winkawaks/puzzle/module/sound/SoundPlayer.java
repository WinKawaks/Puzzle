package cn.xxx.winkawaks.puzzle.module.sound;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import cn.xxx.winkawaks.puzzle.R;

/**
 * Created by 54713 on 2017/11/29.
 */

public class SoundPlayer {
    // SoundPool对象
    public SoundPool mSoundPlayer = new SoundPool(4,
        AudioManager.STREAM_MUSIC, 1);

    // 上下文
    private Context mContext;

    /**
     * 初始化
     */
    public void init(Context context) {
        // 初始化声音
        mContext = context;

        mSoundPlayer.load(mContext, R.raw.bell01, 1);
        mSoundPlayer.load(mContext, R.raw.bell02, 1);
        mSoundPlayer.load(mContext, R.raw.bell03, 1);
        mSoundPlayer.load(mContext, R.raw.bell04, 1);
        mSoundPlayer.load(mContext, R.raw.bell05, 1);
        mSoundPlayer.load(mContext, R.raw.bell06, 1);
        mSoundPlayer.load(mContext, R.raw.bell07, 1);
        mSoundPlayer.load(mContext, R.raw.bell08, 1);
        mSoundPlayer.load(mContext, R.raw.bell09, 1);
        mSoundPlayer.load(mContext, R.raw.bell10, 1);

        mSoundPlayer.load(mContext, R.raw.bell11, 1);
        mSoundPlayer.load(mContext, R.raw.bell12, 1);
        mSoundPlayer.load(mContext, R.raw.bell13, 1);
        mSoundPlayer.load(mContext, R.raw.bell14, 1);
        mSoundPlayer.load(mContext, R.raw.bell15, 1);
        mSoundPlayer.load(mContext, R.raw.bell16, 1);
        mSoundPlayer.load(mContext, R.raw.bell17, 1);
        mSoundPlayer.load(mContext, R.raw.bell18, 1);
        mSoundPlayer.load(mContext, R.raw.bell19, 1);
        mSoundPlayer.load(mContext, R.raw.bell20, 1);

        mSoundPlayer.load(mContext, R.raw.bell21, 1);
        mSoundPlayer.load(mContext, R.raw.bell22, 1);
        mSoundPlayer.load(mContext, R.raw.bell23, 1);
        mSoundPlayer.load(mContext, R.raw.bell24, 1);
        mSoundPlayer.load(mContext, R.raw.bell25, 1);
        mSoundPlayer.load(mContext, R.raw.bell26, 1);
        mSoundPlayer.load(mContext, R.raw.bell27, 1);
        mSoundPlayer.load(mContext, R.raw.bell28, 1);
        mSoundPlayer.load(mContext, R.raw.bell29, 1);
        mSoundPlayer.load(mContext, R.raw.bell30, 1);

        mSoundPlayer.load(mContext, R.raw.bell31, 1);
        mSoundPlayer.load(mContext, R.raw.bell32, 1);
        mSoundPlayer.load(mContext, R.raw.bell33, 1);
        mSoundPlayer.load(mContext, R.raw.bell34, 1);
        mSoundPlayer.load(mContext, R.raw.bell35, 1);
        mSoundPlayer.load(mContext, R.raw.bell36, 1);

    }

    /**
     * 播放声音
     */
    public void play(int soundID) {
        mSoundPlayer.play(soundID, 1, 1, 1, 0, 1);
    }

    public void unload() {
        for (int i = 1; i <= 36; i++) {
            mSoundPlayer.unload(i);
        }
        mSoundPlayer.release();
        mSoundPlayer = null;
    }

}

