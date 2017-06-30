package com.example.luisle.interviewtest.map;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by LuisLe on 6/30/2017.
 */

public interface ServiceContract {

    interface OnDirectionLoaded{
        void onLoaded(@NonNull String origin, @NonNull String destination,
                      @NonNull String originAddress, @NonNull String destinationAddress,
                      @NonNull String polylinePoints);
        void onFailed();
    }

    interface OnLocationLoaded {
        void onLoaded(@NonNull LatLng latLng, @NonNull String placeName);
        void onFailed();
    }

    void getLocation(@NonNull String placeName, @NonNull String query, @NonNull OnLocationLoaded callback);

}
