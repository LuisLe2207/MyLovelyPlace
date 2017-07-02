package com.example.luisle.interviewtest;

import android.app.Activity;
import android.content.pm.ActivityInfo;

/**
 * Created by LuisLe on 7/2/2017.
 */

public class TestUtils {

    public static void rotateToLandscape(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public static void rotateToPortrait(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

}
