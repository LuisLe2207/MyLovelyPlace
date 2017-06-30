package com.example.luisle.interviewtest.direction;

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
 * Created by LuisLe on 6/30/2017.
 */

public class MapPresenter implements MapContract.Presenter{

    @Inject
    Service service;

    private final PlacesRepository placesRepository;

    private final MapContract.View view;

    private Context context;

    @NonNull
    private final String placeID;

    @Inject
    public MapPresenter(PlacesRepository placesRepository, MapContract.View view, Context context, @NonNull String placeID) {
        this.placesRepository = placesRepository;
        this.view = view;
        this.context = context;
        this.placeID = placeID;
    }

    @Inject
    void setupPresenter() {
        view.setPresenter(this);

        final Activity activity = (Activity) context;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DaggerAppComponent.builder()
                        .serviceComponent(((MyApp)activity.getApplication()).getServiceComponent()).build()
                        .inject(MapPresenter.this);
            }
        }, 2000);
    }

    @Override
    public void start() {
        view.showProgressDlg();
        placesRepository.getPlace(placeID, new IPlacesDataSource.GetPlaceCallback() {
            @Override
            public void onPlaceLoaded(Place place) {

                setToolbarTitle(place.getPlaceName());

                String startPoint = "Duong 11, Phuong 11, Go Vap, Tp.Ho Chi Minh";
                String endPoint = place.getPlaceName() + " " + place.getPlaceAddress();
                service.getDirection(startPoint, endPoint, new ServiceContract.OnDirectionLoaded() {
                    @Override
                    public void onLoaded(@NonNull LatLng origin, @NonNull LatLng destination,
                                         @NonNull String originAddress, @NonNull String destinationAddress,
                                         @NonNull String polylinePoints) {
                        view.drawRoutes(origin, destination, originAddress, destinationAddress, polylinePoints);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                view.hideProgressDlg();
                            }
                        }, 2000);
                    }

                    @Override
                    public void onFailed() {

                    }
                });
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void setToolbarTitle(String title) {
        view.setToolbarTitle(title);
    }
}
