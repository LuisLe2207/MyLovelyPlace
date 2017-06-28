package com.example.luisle.interviewtest.places;

import com.example.luisle.interviewtest.data.Place;
import com.example.luisle.interviewtest.data.source.PlacesRepository;
import com.example.luisle.interviewtest.data.source.db.IPlacesDataSource;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by LuisLe on 6/28/2017.
 */

public class PlacesPresenter implements PlacesContract.Presenter{

    private final PlacesRepository placeRepository;

    private final PlacesContract.View view;


    @Inject
    public PlacesPresenter(PlacesRepository placeRepository, PlacesContract.View view) {
        this.placeRepository = placeRepository;
        this.view = view;
    }

    @Inject
    void setupPresenter() {
        view.setPresenter(this);
    }

    @Override
    public void start() {
        loadPlaces();
    }

    @Override
    public void loadPlaces() {
        placeRepository.getPlaces(new IPlacesDataSource.LoadPlacesCallback() {
            @Override
            public void onPlacesLoaded(List<Place> places) {
                view.hideNoDataAvailable();
                view.showPlaces(places);
            }

            @Override
            public void onDataNotAvailable() {
                view.showNoDataAvailable();
            }
        });
    }

    @Override
    public void addNewPlace() {
        view.showAddPlaceUi();
    }
}
