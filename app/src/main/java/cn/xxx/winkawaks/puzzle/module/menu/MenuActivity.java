package cn.xxx.winkawaks.puzzle.module.menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.Toast;
import cn.xxx.winkawaks.puzzle.R;
import cn.xxx.winkawaks.puzzle.database.RecordBean;
import cn.xxx.winkawaks.puzzle.database.RecordSelectHelper;
import cn.xxx.winkawaks.puzzle.module.game.GameActivity;
import cn.xxx.winkawaks.puzzle.module.setting.SettingActivity;
import cn.xxx.winkawaks.puzzle.module.sound.BGMService;
import cn.xxx.winkawaks.puzzle.module.utils.TimeUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executors;

/**
 * Created by 54713 on 2018/1/18.
 */

public class MenuActivity extends Activity implements View.OnClickListener, GestureDetector.OnGestureListener, View.OnTouchListener {
    private Button mBtnStart;
    private Button mBtnSetting;
    private Button mBtnRecord;
    private Rect mStartRect;
    private Rect mSettingRect;
    private Rect mRecordRect;
    private GestureDetector mGestureDetector;
    private ArrayList<RecordBean> mContentSections = new ArrayList<RecordBean>();
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initView();
        initListener();
    }

    private void initListener() {
        mGestureDetector = new GestureDetector(this);
        mBtnStart.setOnClickListener(this);
        mBtnSetting.setOnClickListener(this);
        mBtnRecord.setOnClickListener(this);
        mBtnStart.setOnTouchListener(this);
        mBtnSetting.setOnTouchListener(this);
        mBtnRecord.setOnTouchListener(this);
    }

    private void initView() {
        mContext = this;
        mBtnStart = findViewById(R.id.btn_start);
        mBtnSetting = findViewById(R.id.btn_setting);
        mBtnRecord = findViewById(R.id.btn_record);
        mStartRect = new Rect();
        mBtnStart.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mBtnStart.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mBtnStart.getHitRect(mStartRect);
            }
        });
        mSettingRect = new Rect();
        mBtnSetting.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mBtnSetting.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mBtnSetting.getHitRect(mSettingRect);
            }
        });
        mRecordRect = new Rect();
        mBtnRecord.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mBtnRecord.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mBtnRecord.getHitRect(mRecordRect);
            }
        });
    }

    @Override
    public void onClick(View view) {
        ((Button)view).setBackground(getResources().getDrawable(R.drawable.button_rect));
        ((Button)view).setTextColor(Color.WHITE);
        switch (view.getId()) {
            case R.id.btn_start:
                Intent intent = new Intent(this, GameActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_setting:
                intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_record:
                LoadDBDataTask loadDBDataTask = new LoadDBDataTask();
                loadDBDataTask.executeOnExecutor(Executors.newCachedThreadPool());
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            mBtnStart.setBackground(getResources().getDrawable(R.drawable.button_rect));
            mBtnStart.setTextColor(Color.WHITE);
            mBtnSetting.setBackground(getResources().getDrawable(R.drawable.button_rect));
            mBtnSetting.setTextColor(Color.WHITE);
            mBtnRecord.setBackground(getResources().getDrawable(R.drawable.button_rect));
            mBtnRecord.setTextColor(Color.WHITE);
        }
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float v, float v1) {
        if (mStartRect.contains((int) e2.getX(), (int) e2.getY())) {
            mBtnStart.setBackground(getResources().getDrawable(R.drawable.button_rect_pressed));
            mBtnStart.setTextColor(Color.BLACK);
        } else {
            mBtnStart.setBackground(getResources().getDrawable(R.drawable.button_rect));
            mBtnStart.setTextColor(Color.WHITE);
        }
        if (mSettingRect.contains((int) e2.getX(), (int) e2.getY())) {
            mBtnSetting.setBackground(getResources().getDrawable(R.drawable.button_rect_pressed));
            mBtnSetting.setTextColor(Color.BLACK);
        } else {
            mBtnSetting.setBackground(getResources().getDrawable(R.drawable.button_rect));
            mBtnSetting.setTextColor(Color.WHITE);
        }
        if (mRecordRect.contains((int) e2.getX(), (int) e2.getY())) {
            mBtnRecord.setBackground(getResources().getDrawable(R.drawable.button_rect_pressed));
            mBtnRecord.setTextColor(Color.BLACK);
        } else {
            mBtnRecord.setBackground(getResources().getDrawable(R.drawable.button_rect));
            mBtnRecord.setTextColor(Color.WHITE);
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getX() < 0 || event.getY() < 0 || event.getX() > v.getMeasuredWidth() || event.getY() > v.getMeasuredHeight()) {
            ((Button)v).setBackground(getResources().getDrawable(R.drawable.button_rect));
            ((Button)v).setTextColor(Color.WHITE);
        } else {
            ((Button)v).setBackground(getResources().getDrawable(R.drawable.button_rect_pressed));
            ((Button)v).setTextColor(Color.BLACK);
        }
        return false;
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
                    MenuActivity.this.finish();
                    Intent intent = new Intent(MenuActivity.this, BGMService.class);
                    stopService(intent);
                }
            });
            builder.show();
        }
        return super.onKeyDown(keyCode, event);
    }

    class LoadDBDataTask extends AsyncTask<String, Void, String> {
        RecordSelectHelper newsContentHelper = new RecordSelectHelper(mContext);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            newsContentHelper.open();
        }

        @Override
        protected String doInBackground(String... parm) {
            mContentSections = newsContentHelper.getSectionsListInfo();
            return "";
        }

        @Override
        protected void onPostExecute(String msg) {
            if (mContentSections.size() > 0) {
                int total = mContentSections.size() > 10 ? 10 : mContentSections.size();
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < total - 1; i++) {
                    stringBuffer.append(getCapsInt(i));
                    stringBuffer.append(" " + getString(R.string.time_record));
                    stringBuffer.append(TimeUtil.getTime(Long.parseLong(mContentSections.get(i).getRecord())));
                    stringBuffer.append(" " + getString(R.string.step_record));
                    stringBuffer.append(mContentSections.get(i).getStep());
                    stringBuffer.append(" " + getFormatTime(Long.parseLong(mContentSections.get(i).getCurrentTime())));
                    stringBuffer.append("\n");
                }
                stringBuffer.append(getCapsInt(total - 1));
                stringBuffer.append(" " + getString(R.string.time_record));
                stringBuffer.append(TimeUtil.getTime(Long.parseLong(mContentSections.get(total - 1).getRecord())));
                stringBuffer.append(" " + getString(R.string.step_record));
                stringBuffer.append(mContentSections.get(total - 1).getStep());
                stringBuffer.append(" " + getFormatTime(Long.parseLong(mContentSections.get(total - 1).getCurrentTime())));
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(R.string.record_top_10);
                builder.setMessage(stringBuffer)
                    .setNegativeButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
            } else {
                Toast.makeText(mContext, getString(R.string.no_record), Toast.LENGTH_SHORT).show();
            }
            newsContentHelper.close();
        }

    }

    public static String getFormatTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        String formatTime = format.format(date);
        return formatTime;
    }

    public static String getCapsInt(int i) {
        switch (i) {
            case 0:
                return "壹";
            case 1:
                return "贰";
            case 2:
                return "叁";
            case 3:
                return "肆";
            case 4:
                return "伍";
            case 5:
                return "陆";
            case 6:
                return "柒";
            case 7:
                return "捌";
            case 8:
                return "玖";
            case 9:
                return "拾";
        }
        return "";
    }
}
