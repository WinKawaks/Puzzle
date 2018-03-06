package cn.xxx.winkawaks.puzzle.module.utils;

/**
 * Created by 54713 on 2018/1/25.
 */

public class TimeUtil {
    public static long START_TIME;

    public static void setStartTime() {
        START_TIME = System.currentTimeMillis();
    }

    public static String getTime() {
        long time = System.currentTimeMillis() - START_TIME;
        String ms = time / 100 % 10 + "" + time / 10 % 10;
        long min = time / 60000;
        long s = time / 1000 - 60 * min;
        String minute;
        String second;
        if (min < 10) {
            minute = "0" + min;
        } else {
            minute = String.valueOf(min);
        }
        if (s < 10) {
            second = "0" + s;
        } else {
            second = String.valueOf(s);
        }
        return minute + ":" + second + "." + ms;
    }

    public static String getTime(long time) {
        String ms = time / 100 % 10 + "" + time / 10 % 10;
        long min = time / 60000;
        long s = time / 1000 - 60 * min;
        String minute;
        String second;
        if (min < 10) {
            minute = "0" + min;
        } else {
            minute = String.valueOf(min);
        }
        if (s < 10) {
            second = "0" + s;
        } else {
            second = String.valueOf(s);
        }
        return minute + ":" + second + "." + ms;
    }

    public static long getMs(String time) {
        long ms = 0;
        int m1 = Integer.parseInt(String.valueOf(time.charAt(0)));
        int m2 = Integer.parseInt(String.valueOf(time.charAt(1)));
        ms += (m1 * 10 + m2) * 60000;
        int s1 = Integer.parseInt(String.valueOf(time.charAt(3)));
        int s2 = Integer.parseInt(String.valueOf(time.charAt(4)));
        ms += (s1 * 10 + s2) * 1000;
        int ms1 = Integer.parseInt(String.valueOf(time.charAt(6)));
        int ms2 = Integer.parseInt(String.valueOf(time.charAt(7)));
        ms += (ms1 * 10 + ms2) * 10;
        return ms;
    }

}
