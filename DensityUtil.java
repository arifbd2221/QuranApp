package qapp.mangosoft.com.qappproject;

import android.content.Context;

/**
 * Created by User on 2/21/2018.
 */

public class DensityUtil {

    private DensityUtil() {
    }

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}
