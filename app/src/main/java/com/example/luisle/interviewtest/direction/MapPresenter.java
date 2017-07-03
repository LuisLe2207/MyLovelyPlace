package com.example.luisle.interviewtest.direction;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.example.luisle.interviewtest.DaggerAppComponent;
import com.example.luisle.interviewtest.MyApp;
import com.example.luisle.interviewtest.R;
import com.example.luisle.interviewtest.data.Place;
import com.example.luisle.interviewtest.data.source.PlacesRepository;
import com.example.luisle.interviewtest.data.source.db.IPlacesDataSource;
import com.example.luisle.interviewtest.map.Service;
import com.example.luisle.interviewtest.map.ServiceContract;
import com.example.luisle.interviewtest.utils.AppUtils;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

/**
 * Created by LuisLe on 6/30/2017.
 */

public class MapPresenter implements MapContract.Presenter, IPlacesDataSource.GetPlaceCallback {

    @Inject
    Service service;

    @NonNull
    private final String placeID;

    private final PlacesRepository placesRepository;

    private final MapContract.View view;

    private Context context;

    private Place destinationPlace;

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

        // Create the service
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DaggerAppComponent.builder()
                        .serviceComponent(((MyApp) activity.getApplication()).getServiceComponent()).build()
                        .inject(MapPresenter.this);
            }
        }, 2000);
    }

    private void setDestinationPlace(Place place) {
        this.destinationPlace = place;
    }


    @Override
    public void start() {
        placesRepository.getPlace(placeID, this);
        view.showProgressDlg();

        if (!AppUtils.checkPlayServices(context)) {
            view.hideProgressDlg();
            view.showWarningDialog(context.getResources().getString(R.string.error_google_play_services_not_available));
        }
    }

    @Override
    public void getRoutes(Location currentLocation) {

        String startPoint = currentLocation.getLatitude() + "," + currentLocation.getLongitude();
        String endPoint = destinationPlace.getPlaceName() + " " + destinationPlace.getPlaceAddress();
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
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.hideProgressDlg();
                        view.showWarningDialog(context.getResources().getString(R.string.error_get_direction_error));
                    }
                }, 2000);

            }
        });
    }

    @Override
    public void setToolbarTitle(String title) {
        view.setToolbarTitle(title);
    }

    @Override
    public void onPlaceLoaded(Place place) {
        setDestinationPlace(place);
        setToolbarTitle(place.getPlaceName());
    }

    @Override
    public void onDataNotAvailable() {

    }
}
