package com.example.luisle.interviewtest.direction;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;

/**
 * Created by LuisLe on 6/30/2017.
 */

@Module
public class MapPresenterModule {

    private final MapContract.View view;

    private String placeID;

    public MapPresenterModule(MapContract.View view, String placeID) {
        this.view = view;
        this.placeID = placeID;
    }

    @Provides
    MapContract.View provideMapContractView() {
        return view;
    }

    @Provides
    @NonNull
    String providePlaceID() {
        return placeID;
    }

}
