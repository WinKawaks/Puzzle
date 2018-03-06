package cn.xxx.winkawaks.puzzle.module.setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import cn.xxx.winkawaks.puzzle.R;
import cn.xxx.winkawaks.puzzle.module.sound.BGMService;
import cn.xxx.winkawaks.puzzle.module.utils.LaunchEmailUtil;


/**
 * Created by 54713 on 2017/10/19.
 */

public class SettingActivity extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private CheckBox mCBMusic;
    private CheckBox mCBSound;
    private TextView mTVEmail;
    private Button mBtnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        initListener();
    }

    private void initView() {
        mCBMusic = (CheckBox) findViewById(R.id.checkbox_music);
        mCBSound = (CheckBox) findViewById(R.id.checkbox_sound);
        mTVEmail = (TextView) findViewById(R.id.email);
        mBtnBack = (Button) findViewById(R.id.btn_back);
        SharedPreferences mSharedPreferences = getSharedPreferences("WinKawaks", Context.MODE_PRIVATE);
        Boolean musicOn = mSharedPreferences.getBoolean("music", true);
        Boolean soundOn = mSharedPreferences.getBoolean("sound", true);
        mCBMusic.setChecked(musicOn);
        mCBSound.setChecked(soundOn);
    }

    private void initListener() {
        mTVEmail.setOnClickListener(this);
        mBtnBack.setOnClickListener(this);
        mCBMusic.setOnCheckedChangeListener(this);
        mCBSound.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.email:
                sendEmails();
                break;
            case R.id.btn_back:
                this.finish();
                break;
        }
    }

    private void sendEmails() {
        LaunchEmailUtil.launchEmailToIntent(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SharedPreferences mSharedPreferences = getSharedPreferences("WinKawaks", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        switch (buttonView.getId()) {
            case R.id.checkbox_music:
                editor.putBoolean("music", isChecked);
                Intent intent = new Intent(SettingActivity.this, BGMService.class);
                if (isChecked) {
                    startService(intent);
                } else {
                    stopService(intent);
                }
                break;
            case R.id.checkbox_sound:
                editor.putBoolean("sound", isChecked);
                break;
        }
        editor.apply();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
