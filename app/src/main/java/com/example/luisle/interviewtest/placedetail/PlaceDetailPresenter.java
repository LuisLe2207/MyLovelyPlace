package com.example.luisle.interviewtest.placedetail;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.example.luisle.interviewtest.data.Place;
import com.example.luisle.interviewtest.data.source.PlacesRepository;
import com.example.luisle.interviewtest.data.source.db.IPlacesDataSource;

import javax.inject.Inject;

/**
 * Created by LuisLe on 6/28/2017.
 */

public class PlaceDetailPresenter implements PlaceDetailContract.Presenter, IPlacesDataSource.GetPlaceCallback{

    private final PlacesRepository placesRepository;

    private final PlaceDetailContract.View view;

    @NonNull
    private final String placeID;

    @Inject
    public PlaceDetailPresenter(PlacesRepository placesRepository, PlaceDetailContract.View view, @NonNull String placeID) {
        this.placesRepository = placesRepository;
        this.view = view;
        this.placeID = placeID;
    }

    @Inject
    void setupPresenter() {
        view.setPresenter(this);
    }

    @Override
    public void start() {
        populatePlace();
    }

    @Override
    public void deletePlace() {
        view.showProgressDlg();
        placesRepository.deletePlace(placeID);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.hideProgressDlg();
                view.showPlaces();
            }
        }, 2000);
    }

    @Override
    public void findRoute(@NonNull Place place) {

    }

    @Override
    public void populatePlace() {
        placesRepository.getPlace(placeID, this);
    }

    @Override
    public void openEditPlaceUi() {
        view.showPlaceEditUi(placeID);
    }

    @Override
    public void openDeleteAlertDlg() {
        view.showAlertDlg();
    }

    @Override
    public void onPlaceLoaded(Place place) {
        view.setData(place);
    }

    @Override
    public void onDataNotAvailable() {

    }
}
