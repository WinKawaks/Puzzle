package cn.xxx.winkawaks.puzzle.module.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.style.ForegroundColorSpan;
import cn.xxx.winkawaks.puzzle.R;

/**
 * Created by 54713 on 2018/3/7.
 */

public class ColorTextUtil {
    public static String getLevelText(Context context, String s) {
        int ms = TimeUtil.getMs(s);
        if (ms < 70000) {
            return context.getString(R.string.level_gold);
        } else if (ms < 90000) {
            return context.getString(R.string.level_orange);
        } else if (ms < 120000) {
            return context.getString(R.string.level_green);
        } else if (ms < 150000) {
            return context.getString(R.string.level_purple);
        } else if (ms < 180000) {
            return context.getString(R.string.level_blue);
        } else if (ms < 300000) {
            return context.getString(R.string.level_white);
        } else {
            return context.getString(R.string.level_grey);
        }
    }

    public static ForegroundColorSpan getLevelColor(Context context, String s) {
        int ms = TimeUtil.getMs(s);
        if (ms < 70000) {
            return new ForegroundColorSpan(Color.parseColor("#FFFF00"));
        } else if (ms < 90000) {
            return new ForegroundColorSpan(Color.parseColor("#FF6600"));
        } else if (ms < 120000) {
            return new ForegroundColorSpan(Color.parseColor("#00FF00"));
        } else if (ms < 150000) {
            return new ForegroundColorSpan(Color.parseColor("#AA22FF"));
        } else if (ms < 180000) {
            return new ForegroundColorSpan(Color.parseColor("#1E99FF"));
        } else if (ms < 300000) {
            return new ForegroundColorSpan(Color.parseColor("#FFFFFF"));
        } else {
            return new ForegroundColorSpan(Color.parseColor("#CCCCCC"));
        }
    }
}
