package com.example.luisle.interviewtest.utils;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.makeramen.roundedimageview.RoundedDrawable;

import java.io.ByteArrayOutputStream;

/**
 * Created by LuisLe on 6/27/2017.
 */

public final class AppUtils {

    public static final String PLACE_FRAGMENT_TAG = "PLACE_FRAGMENT";
    public static final String ADD_EDIT_FRAGMENT_TAG = "ADD_EDIT_FRAGMENT_TAG";
    public static final String DETAIL_FRAGMENT_TAG = "DETAIL_FRAGMENT_TAG";

    public static final String  PORTRAIT_STACK = "PORTRAIT_STACK";
    public static final String  LANDSCAPE_STACK = "LANDSCAPE_STACK";

    public static byte[] imageViewToByte(ImageView img){

        RoundedDrawable drawable = (RoundedDrawable) img.getDrawable();
        Bitmap bmp = drawable.getSourceBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static boolean deviceIsTabletAndInLandscape(Activity activity) {
        // Get device size
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        float widthInches = displayMetrics.widthPixels / displayMetrics.xdpi;
        float heightInches = displayMetrics.heightPixels / displayMetrics.ydpi;

        double diagonalInches = Math.sqrt(Math.pow(widthInches, 2) + Math.pow(heightInches, 2));


        // Get Device Orientation
        Configuration configuration = activity.getResources().getConfiguration();

        // Device's smallest width and in landscape
        return (diagonalInches > 6.8 && Configuration.ORIENTATION_LANDSCAPE == configuration.orientation);
    }

    public interface Communicator {
        void setActionBarTitle(@NonNull String title);
    }
}
