package com.example.luisle.interviewtest.places;

import com.example.luisle.interviewtest.BasePresenter;
import com.example.luisle.interviewtest.BaseView;
import com.example.luisle.interviewtest.data.Place;

import java.util.List;

/**
 * Created by LuisLe on 6/28/2017.
 */

public interface PlacesContract {

    interface View extends BaseView<Presenter> {
        void showPlaces(List<Place> places);
        void showAddPlaceUi();
        void showNoDataAvailable();
        void hideNoDataAvailable();
    }

    interface Presenter extends BasePresenter {
        void loadPlaces();
        void addNewPlace();
    }
}
