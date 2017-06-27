package com.example.luisle.interviewtest.data.source.db;

import android.support.annotation.NonNull;

import com.example.luisle.interviewtest.data.Place;

import java.util.List;

/**
 * Created by LuisLe on 6/27/2017.
 */

public interface IPlacesDataSource {
    interface LoadPlacesCallback {

        void onPlacesLoaded(List<Place> places);

        void onDataNotAvailable();
    }

    interface GetPlaceCallback {

        void onPlaceLoaded(Place place);

        void onDataNotAvailable();
    }

    void getPlaces(@NonNull LoadPlacesCallback callback);
    void getPlace(@NonNull String placeID, @NonNull GetPlaceCallback callback);
    void addPlace(@NonNull Place place);
    void updatePlace(@NonNull Place place);
    void deletePlace(@NonNull String placeID);
}
