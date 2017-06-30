package com.example.luisle.interviewtest.placedetail;

import android.support.annotation.NonNull;

import com.example.luisle.interviewtest.BasePresenter;
import com.example.luisle.interviewtest.BaseView;
import com.example.luisle.interviewtest.data.Place;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by LuisLe on 6/28/2017.
 */

public interface PlaceDetailContract {

    interface View extends BaseView<Presenter>, BaseView.ViewProgress {
        void showPlaces();
        void showPlaceEditUi(@NonNull String placeID);
        void setPlaceOnMap(LatLng latLng, String placeName);
        void setData(@NonNull Place place);
        void showAlertDlg();
    }

    interface Presenter extends BasePresenter {
        void deletePlace();
        void findRoute(@NonNull Place place);
        void populatePlace();
        void openEditPlaceUi();
        void openDeleteAlertDlg();
    }

}
