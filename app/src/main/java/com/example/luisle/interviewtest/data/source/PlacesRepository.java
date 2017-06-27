package com.example.luisle.interviewtest.data.source;

import android.support.annotation.NonNull;

import com.example.luisle.interviewtest.data.Place;
import com.example.luisle.interviewtest.data.source.db.IPlacesDataSource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by LuisLe on 6/27/2017.
 */

@Singleton
public class PlacesRepository implements IPlacesDataSource{

    private final IPlacesDataSource dataSource;


    @Inject
    public PlacesRepository(IPlacesDataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public void getPlaces(@NonNull final LoadPlacesCallback callback) {
        dataSource.getPlaces(new LoadPlacesCallback() {
            @Override
            public void onPlacesLoaded(List<Place> places) {
                callback.onPlacesLoaded(places);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getPlace(@NonNull String placeID, @NonNull final GetPlaceCallback callback) {
        dataSource.getPlace(placeID, new GetPlaceCallback() {
            @Override
            public void onPlaceLoaded(Place place) {
                callback.onPlaceLoaded(place);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void addPlace(@NonNull Place place) {
        dataSource.addPlace(place);
    }

    @Override
    public void updatePlace(@NonNull Place place) {
        dataSource.updatePlace(place);
    }

    @Override
    public void deletePlace(@NonNull String placeID) {
        dataSource.deletePlace(placeID);
    }

}
