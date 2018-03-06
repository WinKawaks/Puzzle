package cn.xxx.winkawaks.puzzle.view;

import android.graphics.Bitmap;
import cn.xxx.winkawaks.puzzle.module.touchInterface.TouchChecker;

/**
 * Created by 54713 on 2018/1/20.
 */

public class BitmapTouchChecker implements TouchChecker {

    private Bitmap bitmap;
    private int bmpH = 0;
    private int bmpW = 0;

    public BitmapTouchChecker(Bitmap bitmap) {
        this.bitmap = bitmap;
        bmpH = bitmap.getHeight();
        bmpW = bitmap.getWidth();
    }

    @Override
    public boolean isInTouchArea(int x, int y, int width, int height) {
        int conversionX = (x * bmpW) / width;
        int conversionY = (y * bmpH) / height;

        if (bitmap != null) {
            int pixel = bitmap.getPixel(conversionX, conversionY);
            if (((pixel >> 24) & 0xff) > 0) {
                return true;
            }
        }
        return false;
    }
}
