package com.example.luisle.interviewtest.addeditplace;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.luisle.interviewtest.data.Place;
import com.example.luisle.interviewtest.data.source.PlacesRepository;
import com.example.luisle.interviewtest.data.source.db.IPlacesDataSource.GetPlaceCallback;

import javax.inject.Inject;

/**
 * Created by LuisLe on 6/28/2017.
 */

public class AddEditPlacePresenter implements AddEditPlaceContract.Presenter, GetPlaceCallback {

    private final PlacesRepository placesRepository;

    private final AddEditPlaceContract.View view;

    @Nullable
    private final String placeID;

    @Inject
    public AddEditPlacePresenter(PlacesRepository placesRepository, AddEditPlaceContract.View view, @Nullable String placeID) {
        this.placesRepository = placesRepository;
        this.view = view;
        this.placeID = placeID;
    }

    @Inject
    void setupPresenter() {
        view.setPresenter(this);
    }

    private boolean isNewPlace() {
        return placeID == null;
    }

    @Override
    public void start() {
        changeActionBarTitle();
        if (!isNewPlace()) {
            populatePlace();
        }
    }

    @Override
    public void onPlaceLoaded(Place place) {
        view.setData(place);
    }

    @Override
    public void onDataNotAvailable() {
        if (placeID == null) {
            view.showNoPlaceAvailableAlert();
        }
    }

    @Override
    public void save(@NonNull String placeName, @NonNull String placeAddress, @NonNull String placeDescription, @Nullable byte[] placeImage) {
        view.showProgressDlg();
        if (isNewPlace()) {
            Place place = new Place(placeName, placeAddress, placeDescription, placeImage);
            placesRepository.addPlace(place);
        } else {
            Place place = new Place(placeID, placeName, placeAddress, placeDescription, placeImage);
            placesRepository.updatePlace(place);
        }
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                view.hideProgressDlg();
//                view.redirectUI(isNewPlace(), placeID);
//            }
//        }, 2000);
        view.hideProgressDlg();
        view.redirectUI(isNewPlace(), placeID);
    }

    @Override
    public void populatePlace() {
        placesRepository.getPlace(placeID, this);
    }

    @Override
    public void openCamera() {
        view.showCamera();
    }

    @Override
    public void changeActionBarTitle() {
        if (placeID == null) {
            view.updateActionBarTitle(true);
        } else {
            view.updateActionBarTitle(false);
        }
    }
}
