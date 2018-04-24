package qapp.mangosoft.com.qappproject;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.VideoView;

/**
 * Created by User on 2/21/2018.
 */

public class PreviewVideoView extends VideoView {

    public PreviewVideoView(Context context) {
        super(context);
    }

    public PreviewVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PreviewVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = View.MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(w, (int) (w / 0.56f));
    }

}