package com.example.luisle.interviewtest.placedetail;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.example.luisle.interviewtest.DaggerAppComponent;
import com.example.luisle.interviewtest.MyApp;
import com.example.luisle.interviewtest.data.Place;
import com.example.luisle.interviewtest.data.source.PlacesRepository;
import com.example.luisle.interviewtest.data.source.db.IPlacesDataSource;
import com.example.luisle.interviewtest.map.Service;
import com.example.luisle.interviewtest.map.ServiceContract;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

/**
 * Created by LuisLe on 6/28/2017.
 */

public class PlaceDetailPresenter implements PlaceDetailContract.Presenter, IPlacesDataSource.GetPlaceCallback, ServiceContract.OnLocationLoaded{

    @Inject
    Service service;

    @NonNull
    private final String placeID;

    private final PlacesRepository placesRepository;

    private final PlaceDetailContract.View view;

    private Context context;

    @Inject
    public PlaceDetailPresenter(PlacesRepository placesRepository, PlaceDetailContract.View view, @NonNull String placeID, Context context) {
        this.placesRepository = placesRepository;
        this.view = view;
        this.placeID = placeID;
        this.context = context;
    }

    @Inject
    void setupPresenter() {
        view.setPresenter(this);

        final Activity activity = (Activity) context;

        // Create the service
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DaggerAppComponent.builder()
                        .serviceComponent(((MyApp)activity.getApplication()).getServiceComponent()).build()
                        .inject(PlaceDetailPresenter.this);
            }
        }, 2000);

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
    public void findRoute() {
        view.startDirectionActivity(placeID);
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

        final String placeAddress = place.getPlaceAddress();
        final String placeName = place.getPlaceName();

        final String query = placeName + placeAddress;

        // Get place's location
        service.getLocation(placeName, query, this);

    }

    @Override
    public void onDataNotAvailable() {

    }

    @Override
    public void onLoaded(@NonNull final LatLng latLng, @NonNull final String placeName) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setPlaceOnMap(latLng, placeName);
            }
        }, 2000);
    }

    @Override
    public void onFailed() {

    }
}
