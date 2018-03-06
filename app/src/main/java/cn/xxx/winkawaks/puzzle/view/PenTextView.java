package cn.xxx.winkawaks.puzzle.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import java.io.File;

/**
 * Created by 54713 on 2017/10/19.
 */

public class PenTextView extends TextView {
    private static final String FONTS_FOLDER = "fonts";
    private static final String FONT_PEN = FONTS_FOLDER
        + File.separator + "pen.ttf";

    public PenTextView(Context context) {
        super(context);
        init(context);
    }

    public PenTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PenTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        AssetManager assets = context.getAssets();
        final Typeface font = Typeface.createFromAsset(assets,
            FONT_PEN);
        setTypeface(font);
    }
}

