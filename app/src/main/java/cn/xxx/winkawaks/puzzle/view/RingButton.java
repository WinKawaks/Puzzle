package cn.xxx.winkawaks.puzzle.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;
import cn.xxx.winkawaks.puzzle.R;
import cn.xxx.winkawaks.puzzle.module.touchInterface.TouchChecker;

/**
 * Created by 54713 on 2018/1/19.
 */

public class RingButton extends Button {
    public Boolean Up = true;
    private TouchChecker touchChecker;

    public RingButton(Context context) {
        super(context);
        setTouchChecker(new BitmapTouchChecker(BitmapFactory.decodeResource(getResources(), R.mipmap.first_down_ring)));
    }

    public RingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTouchChecker(new BitmapTouchChecker(BitmapFactory.decodeResource(getResources(), R.mipmap.first_down_ring)));
    }

    public RingButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTouchChecker(new BitmapTouchChecker(BitmapFactory.decodeResource(getResources(), R.mipmap.first_down_ring)));
    }

    public void setTouchChecker(TouchChecker touchChecker) {
        this.touchChecker = touchChecker;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (touchChecker != null) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (touchChecker.isInTouchArea((int) event.getX(), (int) event.getY(), getWidth(), getHeight())) {
                    return super.onTouchEvent(event);
                } else {
                    return false;
                }
            }
        }

        return super.onTouchEvent(event);
    }

}
