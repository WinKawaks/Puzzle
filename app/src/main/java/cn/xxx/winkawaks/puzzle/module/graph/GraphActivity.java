package cn.xxx.winkawaks.puzzle.module.graph;

import android.app.Activity;
import android.os.Bundle;
import cn.xxx.winkawaks.puzzle.R;
import com.github.chrisbanes.photoview.PhotoView;

/**
 * Created by 54713 on 2018/3/8.
 */

public class GraphActivity extends Activity {
    private PhotoView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        initView();
    }

    private void initView() {
        photoView = (PhotoView) findViewById(R.id.photo_view);
        photoView.setImageResource(R.mipmap.graph);
    }
}
