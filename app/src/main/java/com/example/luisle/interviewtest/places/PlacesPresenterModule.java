package com.example.luisle.interviewtest.places;

import dagger.Module;
import dagger.Provides;

/**
 * Created by LuisLe on 6/28/2017.
 */

@Module
public class PlacesPresenterModule {
    private final PlacesContract.View view;

    public PlacesPresenterModule(PlacesContract.View view) {
        this.view = view;
    }

    @Provides
    PlacesContract.View providePlacesContractView() {
        return view;
    }
}
