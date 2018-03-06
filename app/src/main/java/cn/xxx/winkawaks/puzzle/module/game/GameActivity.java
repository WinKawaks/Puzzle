package cn.xxx.winkawaks.puzzle.module.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import cn.xxx.winkawaks.puzzle.MyApplication;
import cn.xxx.winkawaks.puzzle.R;
import cn.xxx.winkawaks.puzzle.database.RecordBean;
import cn.xxx.winkawaks.puzzle.database.RecordSelectHelper;
import cn.xxx.winkawaks.puzzle.module.sound.RhythmUtil;
import cn.xxx.winkawaks.puzzle.module.sound.SoundPlayer;
import cn.xxx.winkawaks.puzzle.module.utils.TimeUtil;
import cn.xxx.winkawaks.puzzle.view.LedTextView;
import cn.xxx.winkawaks.puzzle.view.RingButton;

import java.util.HashMap;

/**
 * Created by 54713 on 2018/1/18.
 */

public class GameActivity extends Activity implements View.OnClickListener, View.OnTouchListener {
    private SoundPlayer mSoundPool;
    private RingButton mBtnRing1;
    private RingButton mBtnRing2;
    private RingButton mBtnRing3;
    private RingButton mBtnRing4;
    private RingButton mBtnRing5;
    private RingButton mBtnRing6;
    private RingButton mBtnRing7;
    private RingButton mBtnRing8;
    private RingButton mBtnRing9;
    private ImageView mIVPole1;
    private ImageView mIVPole2;
    private ImageView mIVPole3;
    private ImageView mIVPole4;
    private ImageView mIVPole5;
    private ImageView mIVPole6;
    private ImageView mIVPole7;
    private ImageView mIVPole8;
    private ImageView mIVPole9;
    private ImageView mSword;
    private LedTextView mTimer;
    private LedTextView mStep;
    private HashMap<Integer, Integer> idToNummap = new HashMap<Integer, Integer>();
    private HashMap<Integer, RingButton> numToRingmap = new HashMap<Integer, RingButton>();
    private HashMap<Integer, ImageView> idToPolemap = new HashMap<Integer, ImageView>();
    private Boolean start = true;
    private Boolean finish = false;
    private Boolean timeShow = false;
    private int steps = 0;
    private int musicSteps = 0;
    private Context mContext;
    private Handler mHandler = new Handler();
    private Boolean soundOn;

    private static int DOWN_DISTANCE = 125;
    private static int LEFT_DISTANCE = -17;
    //private boolean ring1_touch = false;
    //private boolean ring2_touch = false;
    private int posY = 0;
    private int curY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initView();
        initListener();
    }

    private void initListener() {
        mBtnRing1.setOnClickListener(this);
        mBtnRing2.setOnClickListener(this);
        mBtnRing3.setOnClickListener(this);
        mBtnRing4.setOnClickListener(this);
        mBtnRing5.setOnClickListener(this);
        mBtnRing6.setOnClickListener(this);
        mBtnRing7.setOnClickListener(this);
        mBtnRing8.setOnClickListener(this);
        mBtnRing9.setOnClickListener(this);
        mBtnRing1.setOnTouchListener(this);
        mBtnRing2.setOnTouchListener(this);
    }

    private void initView() {
        mContext = this;
        SharedPreferences mSharedPreferences = getSharedPreferences("WinKawaks", Context.MODE_PRIVATE);
        soundOn = mSharedPreferences.getBoolean("sound", true);
        if (soundOn) {
            mSoundPool = MyApplication.getSoundPool(getApplication());
        }
        mTimer = findViewById(R.id.timer);
        mTimer.setVisibility(View.VISIBLE);
        mStep = findViewById(R.id.step);
        mStep.setVisibility(View.VISIBLE);
        mSword = findViewById(R.id.iv_sword);
        mBtnRing1 = findViewById(R.id.btn_ring_1);
        mBtnRing2 = findViewById(R.id.btn_ring_2);
        mBtnRing3 = findViewById(R.id.btn_ring_3);
        mBtnRing4 = findViewById(R.id.btn_ring_4);
        mBtnRing5 = findViewById(R.id.btn_ring_5);
        mBtnRing6 = findViewById(R.id.btn_ring_6);
        mBtnRing7 = findViewById(R.id.btn_ring_7);
        mBtnRing8 = findViewById(R.id.btn_ring_8);
        mBtnRing9 = findViewById(R.id.btn_ring_9);

        mIVPole1 = findViewById(R.id.iv_pole_1);
        mIVPole2 = findViewById(R.id.iv_pole_2);
        mIVPole3 = findViewById(R.id.iv_pole_3);
        mIVPole4 = findViewById(R.id.iv_pole_4);
        mIVPole5 = findViewById(R.id.iv_pole_5);
        mIVPole6 = findViewById(R.id.iv_pole_6);
        mIVPole7 = findViewById(R.id.iv_pole_7);
        mIVPole8 = findViewById(R.id.iv_pole_8);
        mIVPole9 = findViewById(R.id.iv_pole_9);

        idToNummap.put(R.id.btn_ring_2, 2);
        idToNummap.put(R.id.btn_ring_3, 3);
        idToNummap.put(R.id.btn_ring_4, 4);
        idToNummap.put(R.id.btn_ring_5, 5);
        idToNummap.put(R.id.btn_ring_6, 6);
        idToNummap.put(R.id.btn_ring_7, 7);
        idToNummap.put(R.id.btn_ring_8, 8);
        idToNummap.put(R.id.btn_ring_9, 9);

        idToPolemap.put(R.id.btn_ring_1, mIVPole1);
        idToPolemap.put(R.id.btn_ring_2, mIVPole2);
        idToPolemap.put(R.id.btn_ring_3, mIVPole3);
        idToPolemap.put(R.id.btn_ring_4, mIVPole4);
        idToPolemap.put(R.id.btn_ring_5, mIVPole5);
        idToPolemap.put(R.id.btn_ring_6, mIVPole6);
        idToPolemap.put(R.id.btn_ring_7, mIVPole7);
        idToPolemap.put(R.id.btn_ring_8, mIVPole8);
        idToPolemap.put(R.id.btn_ring_9, mIVPole9);

        numToRingmap.put(1, mBtnRing1);
        numToRingmap.put(2, mBtnRing2);
        numToRingmap.put(3, mBtnRing3);
        numToRingmap.put(4, mBtnRing4);
        numToRingmap.put(5, mBtnRing5);
        numToRingmap.put(6, mBtnRing6);
        numToRingmap.put(7, mBtnRing7);
        numToRingmap.put(8, mBtnRing8);
        numToRingmap.put(9, mBtnRing9);

        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        DOWN_DISTANCE = (int) (height / 8.64);
        LEFT_DISTANCE = (int) (-width / 112.94);

        ConstraintLayout.LayoutParams swordParams = new ConstraintLayout.LayoutParams(width, height / 9);
        swordParams.bottomToBottom = R.id.bg_game;
        swordParams.leftToLeft = R.id.bg_game;
        swordParams.rightToRight = R.id.bg_game;
        swordParams.topToTop = R.id.bg_game;
        swordParams.verticalBias = 0.277f;
        mSword.setLayoutParams(swordParams);

        ConstraintLayout.LayoutParams pole1Params = new ConstraintLayout.LayoutParams((int) (width / 13.62), (int) (height / 2.01));
        pole1Params.bottomToBottom = R.id.bg_game;
        pole1Params.leftToLeft = R.id.bg_game;
        pole1Params.rightToRight = R.id.bg_game;
        pole1Params.topToTop = R.id.bg_game;
        pole1Params.verticalBias = 0.43f;
        pole1Params.horizontalBias = 0.8843f;
        mIVPole1.setLayoutParams(pole1Params);

        ConstraintLayout.LayoutParams pole2Params = new ConstraintLayout.LayoutParams((int) (width / 13.62), (int) (height / 2.01));
        pole2Params.bottomToBottom = R.id.bg_game;
        pole2Params.leftToLeft = R.id.bg_game;
        pole2Params.rightToRight = R.id.bg_game;
        pole2Params.topToTop = R.id.bg_game;
        pole2Params.verticalBias = 0.43f;
        pole2Params.horizontalBias = 0.8112f;
        mIVPole2.setLayoutParams(pole2Params);

        ConstraintLayout.LayoutParams pole3Params = new ConstraintLayout.LayoutParams((int) (width / 13.62), (int) (height / 2.01));
        pole3Params.bottomToBottom = R.id.bg_game;
        pole3Params.leftToLeft = R.id.bg_game;
        pole3Params.rightToRight = R.id.bg_game;
        pole3Params.topToTop = R.id.bg_game;
        pole3Params.verticalBias = 0.43f;
        pole3Params.horizontalBias = 0.7381f;
        mIVPole3.setLayoutParams(pole3Params);

        ConstraintLayout.LayoutParams pole4Params = new ConstraintLayout.LayoutParams((int) (width / 13.62), (int) (height / 2.01));
        pole4Params.bottomToBottom = R.id.bg_game;
        pole4Params.leftToLeft = R.id.bg_game;
        pole4Params.rightToRight = R.id.bg_game;
        pole4Params.topToTop = R.id.bg_game;
        pole4Params.verticalBias = 0.43f;
        pole4Params.horizontalBias = 0.6648f;
        mIVPole4.setLayoutParams(pole4Params);

        ConstraintLayout.LayoutParams pole5Params = new ConstraintLayout.LayoutParams((int) (width / 13.62), (int) (height / 2.01));
        pole5Params.bottomToBottom = R.id.bg_game;
        pole5Params.leftToLeft = R.id.bg_game;
        pole5Params.rightToRight = R.id.bg_game;
        pole5Params.topToTop = R.id.bg_game;
        pole5Params.verticalBias = 0.43f;
        pole5Params.horizontalBias = 0.5921f;
        mIVPole5.setLayoutParams(pole5Params);

        ConstraintLayout.LayoutParams pole6Params = new ConstraintLayout.LayoutParams((int) (width / 13.62), (int) (height / 2.01));
        pole6Params.bottomToBottom = R.id.bg_game;
        pole6Params.leftToLeft = R.id.bg_game;
        pole6Params.rightToRight = R.id.bg_game;
        pole6Params.topToTop = R.id.bg_game;
        pole6Params.verticalBias = 0.43f;
        pole6Params.horizontalBias = 0.5194f;
        mIVPole6.setLayoutParams(pole6Params);

        ConstraintLayout.LayoutParams pole7Params = new ConstraintLayout.LayoutParams((int) (width / 13.62), (int) (height / 2.01));
        pole7Params.bottomToBottom = R.id.bg_game;
        pole7Params.leftToLeft = R.id.bg_game;
        pole7Params.rightToRight = R.id.bg_game;
        pole7Params.topToTop = R.id.bg_game;
        pole7Params.verticalBias = 0.43f;
        pole7Params.horizontalBias = 0.4461f;
        mIVPole7.setLayoutParams(pole7Params);

        ConstraintLayout.LayoutParams pole8Params = new ConstraintLayout.LayoutParams((int) (width / 13.62), (int) (height / 2.01));
        pole8Params.bottomToBottom = R.id.bg_game;
        pole8Params.leftToLeft = R.id.bg_game;
        pole8Params.rightToRight = R.id.bg_game;
        pole8Params.topToTop = R.id.bg_game;
        pole8Params.verticalBias = 0.43f;
        pole8Params.horizontalBias = 0.3733f;
        mIVPole8.setLayoutParams(pole8Params);

        ConstraintLayout.LayoutParams pole9Params = new ConstraintLayout.LayoutParams((int) (width / 13.62), (int) (height / 2.01));
        pole9Params.bottomToBottom = R.id.bg_game;
        pole9Params.leftToLeft = R.id.bg_game;
        pole9Params.rightToRight = R.id.bg_game;
        pole9Params.topToTop = R.id.bg_game;
        pole9Params.verticalBias = 0.43f;
        pole9Params.horizontalBias = 0.3f;
        mIVPole9.setLayoutParams(pole9Params);

        ConstraintLayout.LayoutParams ring9Params = new ConstraintLayout.LayoutParams((int) (width / 12.8), (int) (height / 3.27));
        ring9Params.bottomToBottom = R.id.bg_game;
        ring9Params.leftToLeft = R.id.bg_game;
        ring9Params.rightToRight = R.id.bg_game;
        ring9Params.topToTop = R.id.bg_game;
        ring9Params.verticalBias = 0.331f;
        ring9Params.horizontalBias = 0.35f;
        mBtnRing9.setLayoutParams(ring9Params);

        ConstraintLayout.LayoutParams ring8Params = new ConstraintLayout.LayoutParams((int) (width / 12.8), (int) (height / 3.27));
        ring8Params.bottomToBottom = R.id.bg_game;
        ring8Params.leftToLeft = R.id.bg_game;
        ring8Params.rightToRight = R.id.bg_game;
        ring8Params.topToTop = R.id.bg_game;
        ring8Params.verticalBias = 0.331f;
        ring8Params.horizontalBias = 0.4233f;
        mBtnRing8.setLayoutParams(ring8Params);

        ConstraintLayout.LayoutParams ring7Params = new ConstraintLayout.LayoutParams((int) (width / 12.8), (int) (height / 3.27));
        ring7Params.bottomToBottom = R.id.bg_game;
        ring7Params.leftToLeft = R.id.bg_game;
        ring7Params.rightToRight = R.id.bg_game;
        ring7Params.topToTop = R.id.bg_game;
        ring7Params.verticalBias = 0.33f;
        ring7Params.horizontalBias = 0.4966f;
        mBtnRing7.setLayoutParams(ring7Params);

        ConstraintLayout.LayoutParams ring6Params = new ConstraintLayout.LayoutParams((int) (width / 12.8), (int) (height / 3.27));
        ring6Params.bottomToBottom = R.id.bg_game;
        ring6Params.leftToLeft = R.id.bg_game;
        ring6Params.rightToRight = R.id.bg_game;
        ring6Params.topToTop = R.id.bg_game;
        ring6Params.verticalBias = 0.328f;
        ring6Params.horizontalBias = 0.5699f;
        mBtnRing6.setLayoutParams(ring6Params);

        ConstraintLayout.LayoutParams ring5Params = new ConstraintLayout.LayoutParams((int) (width / 12.8), (int) (height / 3.27));
        ring5Params.bottomToBottom = R.id.bg_game;
        ring5Params.leftToLeft = R.id.bg_game;
        ring5Params.rightToRight = R.id.bg_game;
        ring5Params.topToTop = R.id.bg_game;
        ring5Params.verticalBias = 0.328f;
        ring5Params.horizontalBias = 0.6432f;
        mBtnRing5.setLayoutParams(ring5Params);

        ConstraintLayout.LayoutParams ring4Params = new ConstraintLayout.LayoutParams((int) (width / 12.8), (int) (height / 3.27));
        ring4Params.bottomToBottom = R.id.bg_game;
        ring4Params.leftToLeft = R.id.bg_game;
        ring4Params.rightToRight = R.id.bg_game;
        ring4Params.topToTop = R.id.bg_game;
        ring4Params.verticalBias = 0.328f;
        ring4Params.horizontalBias = 0.7165f;
        mBtnRing4.setLayoutParams(ring4Params);

        ConstraintLayout.LayoutParams ring3Params = new ConstraintLayout.LayoutParams((int) (width / 12.8), (int) (height / 3.27));
        ring3Params.bottomToBottom = R.id.bg_game;
        ring3Params.leftToLeft = R.id.bg_game;
        ring3Params.rightToRight = R.id.bg_game;
        ring3Params.topToTop = R.id.bg_game;
        ring3Params.verticalBias = 0.327f;
        ring3Params.horizontalBias = 0.7898f;
        mBtnRing3.setLayoutParams(ring3Params);

        ConstraintLayout.LayoutParams ring2Params = new ConstraintLayout.LayoutParams((int) (width / 12.8), (int) (height / 3.27));
        ring2Params.bottomToBottom = R.id.bg_game;
        ring2Params.leftToLeft = R.id.bg_game;
        ring2Params.rightToRight = R.id.bg_game;
        ring2Params.topToTop = R.id.bg_game;
        ring2Params.verticalBias = 0.327f;
        ring2Params.horizontalBias = 0.8631f;
        mBtnRing2.setLayoutParams(ring2Params);

        ConstraintLayout.LayoutParams ring1Params = new ConstraintLayout.LayoutParams((int) (width / 12.8), (int) (height / 3.27));
        ring1Params.bottomToBottom = R.id.bg_game;
        ring1Params.leftToLeft = R.id.bg_game;
        ring1Params.rightToRight = R.id.bg_game;
        ring1Params.topToTop = R.id.bg_game;
        ring1Params.verticalBias = 0.326f;
        ring1Params.horizontalBias = 0.9364f;
        mBtnRing1.setLayoutParams(ring1Params);
    }

    @Override
    public void onClick(View view) {
        start = isStart() && steps == 0;
        if (start) {
            finish = false;
            timeShow = false;
            TimeUtil.setStartTime();
            mHandler.post(run);
            mTimer.setVisibility(View.VISIBLE);
            mStep.setVisibility(View.VISIBLE);
        }
        switch (view.getId()) {
            case R.id.btn_ring_1:
                if (((RingButton) view).Up) {
                    view.setTranslationY(DOWN_DISTANCE);
                    view.setTranslationX(LEFT_DISTANCE);
                    mIVPole1.setTranslationY(DOWN_DISTANCE);
                    mIVPole1.setTranslationX(LEFT_DISTANCE);
                    ((RingButton) view).setBackgroundResource(R.mipmap.first_down_ring);
                    mIVPole1.setBackgroundResource(R.mipmap.down_pole);
                } else {
                    view.setTranslationY(0);
                    view.setTranslationX(0);
                    mIVPole1.setTranslationY(0);
                    mIVPole1.setTranslationX(0);
                    ((RingButton) view).setBackgroundResource(R.mipmap.first_up_ring);
                    mIVPole1.setBackgroundResource(R.mipmap.up_pole);
                }
                ((RingButton) view).Up = !((RingButton) view).Up;
                if (soundOn) {
                    mSoundPool.play(RhythmUtil.RHYTHM[musicSteps % 114]);
                    musicSteps++;
                }
                break;
            case R.id.btn_ring_2:
            case R.id.btn_ring_3:
            case R.id.btn_ring_4:
            case R.id.btn_ring_5:
            case R.id.btn_ring_6:
            case R.id.btn_ring_7:
            case R.id.btn_ring_8:
            case R.id.btn_ring_9:
                if (movePermission(idToNummap.get(view.getId()))) {
                    if (((RingButton) view).Up) {
                        view.setTranslationY(DOWN_DISTANCE);
                        view.setTranslationX(LEFT_DISTANCE);
                        idToPolemap.get(view.getId()).setTranslationY(DOWN_DISTANCE);
                        idToPolemap.get(view.getId()).setTranslationX(LEFT_DISTANCE);
                        ((RingButton) view).setBackgroundResource(R.mipmap.other_down_ring);
                        idToPolemap.get(view.getId()).setBackgroundResource(R.mipmap.down_pole);
                    } else {
                        view.setTranslationY(0);
                        view.setTranslationX(0);
                        idToPolemap.get(view.getId()).setTranslationY(0);
                        idToPolemap.get(view.getId()).setTranslationX(0);
                        ((RingButton) view).setBackgroundResource(R.mipmap.other_up_ring);
                        idToPolemap.get(view.getId()).setBackgroundResource(R.mipmap.up_pole);
                    }
                    ((RingButton) view).Up = !((RingButton) view).Up;
                    if (soundOn) {
                        mSoundPool.play(RhythmUtil.RHYTHM[musicSteps % 114]);
                        musicSteps++;
                    }
                }
                break;
        }
        if (isFinish()) {
            finish = true;
            if (!timeShow) {
                remindTime(mTimer.getText().toString());
                timeShow = true;
            }
        }
        if (finish) {
            steps = 0;
            mTimer.setVisibility(View.INVISIBLE);
            mStep.setVisibility(View.INVISIBLE);
            mHandler.removeCallbacks(run);
        } else {
            steps++;
            StringBuffer sb = new StringBuffer();
            if (steps < 10) {
                sb = new StringBuffer("00").append(steps);
            } else if (steps < 100) {
                sb = new StringBuffer("0").append(steps);
            } else if (steps < 1000) {
                sb = new StringBuffer("").append(steps);
            }
            mStep.setText(sb.toString());
        }
    }

    private Boolean movePermission(int num) {
        if (!numToRingmap.get(num - 1).Up) {
            return false;
        }
        for (int i = 1; i < num - 1; i++) {
            if (numToRingmap.get(i).Up) {
                return false;
            }
        }
        return true;
    }

    private Boolean isStart() {
        for (int i = 1; i <= 9; i++) {
            if (!numToRingmap.get(i).Up) {
                return false;
            }
        }
        return true;
    }

    private Boolean isFinish() {
        for (int i = 1; i <= 9; i++) {
            if (numToRingmap.get(i).Up) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.exit_game)
                .setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton(R.string.confirm, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    GameActivity.this.finish();
                }
            });
            builder.show();
        }
        return super.onKeyDown(keyCode, event);
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            mTimer.setText(TimeUtil.getTime());
            if (TimeUtil.getTime().charAt(0) == '1' && TimeUtil.getTime().charAt(1) >= '5') {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage(R.string.go_die)
                    .setNegativeButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GameActivity.this.finish();
                    }
                });
                builder.show();
            } else {
                mHandler.post(run);
            }
        }
    };

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacks(run);
        mHandler = null;
        super.onDestroy();
    }

    private void remindTime(String time) {
        RecordSelectHelper recordSelectHelper = new RecordSelectHelper(this);
        recordSelectHelper.open();
        RecordBean recordBean = new RecordBean();
        recordBean.setRecord(String.valueOf(TimeUtil.getMs(time)));
        recordBean.setCurrentTime(String.valueOf(System.currentTimeMillis()));
        recordSelectHelper.insertNewsInfo(recordBean);
        recordSelectHelper.close();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.time_title) + time + "\n" + getString(R.string.step_title) + steps)
            .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
        builder.show();
    }

    //@Override
    //public boolean onTouch(View v, MotionEvent event) {
    //    boolean intercept = false;
    //    switch (event.getAction()) {
    //        case MotionEvent.ACTION_DOWN:
    //            start = isStart() && steps == 0;
    //            if (start) {
    //                finish = false;
    //                timeShow = false;
    //                TimeUtil.setStartTime();
    //                mHandler.post(run);
    //                mTimer.setVisibility(View.VISIBLE);
    //                mStep.setVisibility(View.VISIBLE);
    //            }
    //            switch (v.getId()) {
    //                case R.id.btn_ring_1:
    //                    ring1_touch = true;
    //                    if (ring2_touch && mBtnRing1.Up == mBtnRing2.Up) {
    //                        if (((RingButton) v).Up) {
    //                            v.setTranslationY(DOWN_DISTANCE);
    //                            v.setTranslationX(LEFT_DISTANCE);
    //                            mBtnRing2.setTranslationY(DOWN_DISTANCE);
    //                            mBtnRing2.setTranslationX(LEFT_DISTANCE);
    //                            idToPolemap.get(v.getId()).setTranslationY(DOWN_DISTANCE);
    //                            idToPolemap.get(v.getId()).setTranslationX(LEFT_DISTANCE);
    //                            mIVPole2.setTranslationY(DOWN_DISTANCE);
    //                            mIVPole2.setTranslationX(LEFT_DISTANCE);
    //                            ((RingButton) v).setBackgroundResource(R.mipmap.first_down_ring);
    //                            mBtnRing2.setBackgroundResource(R.mipmap.other_down_ring);
    //                            idToPolemap.get(v.getId()).setBackgroundResource(R.mipmap.down_pole);
    //                            mIVPole2.setBackgroundResource(R.mipmap.down_pole);
    //                        } else {
    //                            v.setTranslationY(0);
    //                            v.setTranslationX(0);
    //                            mBtnRing2.setTranslationY(0);
    //                            mBtnRing2.setTranslationX(0);
    //                            idToPolemap.get(v.getId()).setTranslationY(0);
    //                            idToPolemap.get(v.getId()).setTranslationX(0);
    //                            mIVPole2.setTranslationY(0);
    //                            mIVPole2.setTranslationX(0);
    //                            ((RingButton) v).setBackgroundResource(R.mipmap.first_up_ring);
    //                            mBtnRing2.setBackgroundResource(R.mipmap.other_up_ring);
    //                            idToPolemap.get(v.getId()).setBackgroundResource(R.mipmap.up_pole);
    //                            mIVPole2.setBackgroundResource(R.mipmap.up_pole);
    //                        }
    //                        ((RingButton) v).Up = !((RingButton) v).Up;
    //                        mBtnRing2.Up = !mBtnRing2.Up;
    //                        if (soundOn) {
    //                            mSoundPool.play(RhythmUtil.RHYTHM[musicSteps % 114]);
    //                            musicSteps++;
    //                        }
    //                        if (isFinish()) {
    //                            finish = true;
    //                            if (!timeShow) {
    //                                remindTime(mTimer.getText().toString());
    //                                timeShow = true;
    //                            }
    //                        }
    //                        if (finish) {
    //                            steps = 0;
    //                            mTimer.setVisibility(View.INVISIBLE);
    //                            mStep.setVisibility(View.INVISIBLE);
    //                            mHandler.removeCallbacks(run);
    //                        } else {
    //                            steps++;
    //                            StringBuffer sb = new StringBuffer();
    //                            if (steps < 10) {
    //                                sb = new StringBuffer("00").append(steps);
    //                            } else if (steps < 100) {
    //                                sb = new StringBuffer("0").append(steps);
    //                            } else if (steps < 1000) {
    //                                sb = new StringBuffer("").append(steps);
    //                            }
    //                            mStep.setText(sb.toString());
    //                        }
    //                        intercept = true;
    //                    }
    //                    break;
    //                case R.id.btn_ring_2:
    //                    ring2_touch = true;
    //                    if (ring1_touch && mBtnRing1.Up == mBtnRing2.Up) {
    //                        if (((RingButton) v).Up) {
    //                            v.setTranslationY(DOWN_DISTANCE);
    //                            v.setTranslationX(LEFT_DISTANCE);
    //                            mBtnRing1.setTranslationY(DOWN_DISTANCE);
    //                            mBtnRing1.setTranslationX(LEFT_DISTANCE);
    //                            idToPolemap.get(v.getId()).setTranslationY(DOWN_DISTANCE);
    //                            idToPolemap.get(v.getId()).setTranslationX(LEFT_DISTANCE);
    //                            mIVPole1.setTranslationY(DOWN_DISTANCE);
    //                            mIVPole1.setTranslationX(LEFT_DISTANCE);
    //                            ((RingButton) v).setBackgroundResource(R.mipmap.other_down_ring);
    //                            mBtnRing1.setBackgroundResource(R.mipmap.first_down_ring);
    //                            idToPolemap.get(v.getId()).setBackgroundResource(R.mipmap.down_pole);
    //                            mIVPole1.setBackgroundResource(R.mipmap.down_pole);
    //                        } else {
    //                            v.setTranslationY(0);
    //                            v.setTranslationX(0);
    //                            mBtnRing1.setTranslationY(0);
    //                            mBtnRing1.setTranslationX(0);
    //                            idToPolemap.get(v.getId()).setTranslationY(0);
    //                            idToPolemap.get(v.getId()).setTranslationX(0);
    //                            mIVPole1.setTranslationY(0);
    //                            mIVPole1.setTranslationX(0);
    //                            ((RingButton) v).setBackgroundResource(R.mipmap.other_up_ring);
    //                            mBtnRing1.setBackgroundResource(R.mipmap.first_up_ring);
    //                            idToPolemap.get(v.getId()).setBackgroundResource(R.mipmap.up_pole);
    //                            mIVPole1.setBackgroundResource(R.mipmap.up_pole);
    //                        }
    //                        ((RingButton) v).Up = !((RingButton) v).Up;
    //                        mBtnRing1.Up = !mBtnRing1.Up;
    //                        if (soundOn) {
    //                            mSoundPool.play(RhythmUtil.RHYTHM[musicSteps % 114]);
    //                            musicSteps++;
    //                        }
    //                        if (isFinish()) {
    //                            finish = true;
    //                            if (!timeShow) {
    //                                remindTime(mTimer.getText().toString());
    //                                timeShow = true;
    //                            }
    //                        }
    //                        if (finish) {
    //                            steps = 0;
    //                            mTimer.setVisibility(View.INVISIBLE);
    //                            mStep.setVisibility(View.INVISIBLE);
    //                            mHandler.removeCallbacks(run);
    //                        } else {
    //                            steps++;
    //                            StringBuffer sb = new StringBuffer();
    //                            if (steps < 10) {
    //                                sb = new StringBuffer("00").append(steps);
    //                            } else if (steps < 100) {
    //                                sb = new StringBuffer("0").append(steps);
    //                            } else if (steps < 1000) {
    //                                sb = new StringBuffer("").append(steps);
    //                            }
    //                            mStep.setText(sb.toString());
    //                        }
    //                        intercept = true;
    //                    }
    //                    break;
    //            }
    //            break;
    //        case MotionEvent.ACTION_UP:
    //            switch (v.getId()) {
    //                case R.id.btn_ring_1:
    //                    ring1_touch = false;
    //                    break;
    //                case R.id.btn_ring_2:
    //                    ring2_touch = false;
    //                    break;
    //            }
    //            break;
    //    }
    //    return intercept;
    //}

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean intercept = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("WinKawaks", "down");
                posY = (int) event.getRawY();
                start = isStart() && steps == 0;
                if (start) {
                    finish = false;
                    timeShow = false;
                    TimeUtil.setStartTime();
                    mHandler.post(run);
                    mTimer.setVisibility(View.VISIBLE);
                    mStep.setVisibility(View.VISIBLE);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("WinKawaks", "move");
                curY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                Log.i("WinKawaks", "up");
                Log.i("WinKawaks", String.valueOf(posY));
                Log.i("WinKawaks", String.valueOf(curY));
                if (curY - posY > 15) {  //下划
                    if (mBtnRing1.Up && mBtnRing2.Up) {
                        mBtnRing1.setTranslationY(DOWN_DISTANCE);
                        mBtnRing1.setTranslationX(LEFT_DISTANCE);
                        mBtnRing2.setTranslationY(DOWN_DISTANCE);
                        mBtnRing2.setTranslationX(LEFT_DISTANCE);
                        mIVPole1.setTranslationY(DOWN_DISTANCE);
                        mIVPole1.setTranslationX(LEFT_DISTANCE);
                        mIVPole2.setTranslationY(DOWN_DISTANCE);
                        mIVPole2.setTranslationX(LEFT_DISTANCE);
                        mBtnRing1.setBackgroundResource(R.mipmap.first_down_ring);
                        mBtnRing2.setBackgroundResource(R.mipmap.other_down_ring);
                        mIVPole1.setBackgroundResource(R.mipmap.down_pole);
                        mIVPole2.setBackgroundResource(R.mipmap.down_pole);
                        intercept = true;
                        mBtnRing1.Up = !mBtnRing1.Up;
                        mBtnRing2.Up = !mBtnRing2.Up;
                        if (soundOn) {
                            mSoundPool.play(RhythmUtil.RHYTHM[musicSteps % 114]);
                            musicSteps++;
                        }
                        if (isFinish()) {
                            finish = true;
                            if (!timeShow) {
                                remindTime(mTimer.getText().toString());
                                timeShow = true;
                            }
                        }
                        if (finish) {
                            steps = 0;
                            mTimer.setVisibility(View.INVISIBLE);
                            mStep.setVisibility(View.INVISIBLE);
                            mHandler.removeCallbacks(run);
                        } else {
                            steps++;
                            StringBuffer sb = new StringBuffer();
                            if (steps < 10) {
                                sb = new StringBuffer("00").append(steps);
                            } else if (steps < 100) {
                                sb = new StringBuffer("0").append(steps);
                            } else if (steps < 1000) {
                                sb = new StringBuffer("").append(steps);
                            }
                            mStep.setText(sb.toString());
                        }
                    }
                } else if (posY - curY > 15) {  //上划
                    if (!mBtnRing1.Up && !mBtnRing2.Up) {
                        mBtnRing1.setTranslationY(0);
                        mBtnRing1.setTranslationX(0);
                        mBtnRing2.setTranslationY(0);
                        mBtnRing2.setTranslationX(0);
                        mIVPole1.setTranslationY(0);
                        mIVPole1.setTranslationX(0);
                        mIVPole2.setTranslationY(0);
                        mIVPole2.setTranslationX(0);
                        mBtnRing1.setBackgroundResource(R.mipmap.first_up_ring);
                        mBtnRing2.setBackgroundResource(R.mipmap.other_up_ring);
                        mIVPole1.setBackgroundResource(R.mipmap.up_pole);
                        mIVPole2.setBackgroundResource(R.mipmap.up_pole);
                        intercept = true;
                        mBtnRing1.Up = !mBtnRing1.Up;
                        mBtnRing2.Up = !mBtnRing2.Up;
                        if (soundOn) {
                            mSoundPool.play(RhythmUtil.RHYTHM[musicSteps % 114]);
                            musicSteps++;
                        }
                        if (isFinish()) {
                            finish = true;
                            if (!timeShow) {
                                remindTime(mTimer.getText().toString());
                                timeShow = true;
                            }
                        }
                        if (finish) {
                            steps = 0;
                            mTimer.setVisibility(View.INVISIBLE);
                            mStep.setVisibility(View.INVISIBLE);
                            mHandler.removeCallbacks(run);
                        } else {
                            steps++;
                            StringBuffer sb = new StringBuffer();
                            if (steps < 10) {
                                sb = new StringBuffer("00").append(steps);
                            } else if (steps < 100) {
                                sb = new StringBuffer("0").append(steps);
                            } else if (steps < 1000) {
                                sb = new StringBuffer("").append(steps);
                            }
                            mStep.setText(sb.toString());
                        }
                    }
                }
                break;
        }
        return intercept;
    }
}
