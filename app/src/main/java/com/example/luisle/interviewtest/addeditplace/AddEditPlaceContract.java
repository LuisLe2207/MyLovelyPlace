package com.example.luisle.interviewtest.addeditplace;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.luisle.interviewtest.BasePresenter;
import com.example.luisle.interviewtest.BaseView;
import com.example.luisle.interviewtest.data.Place;

/**
 * Created by LuisLe on 6/28/2017.
 */

public interface AddEditPlaceContract {
    interface View extends BaseView<Presenter>, BaseView.ViewProgress {
        void redirectUI(boolean result);
        void setData(@NonNull Place place);
        void showCamera();
        void updateActionBarTitle(boolean isAdd);
        void showNoPlaceAvailableAlert();
    }

    interface Presenter extends BasePresenter {
        void save(@NonNull String placeName, @NonNull String placeAddress, @NonNull String placeDescription,
                  @Nullable byte[] placeImage);
        void populatePlace();
        void openCamera();
        void changeActionBarTitle();
    }
}
