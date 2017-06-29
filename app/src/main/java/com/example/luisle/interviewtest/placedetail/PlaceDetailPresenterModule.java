package com.example.luisle.interviewtest.placedetail;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;

/**
 * Created by LuisLe on 6/28/2017.
 */

@Module
public class PlaceDetailPresenterModule {

    private final PlaceDetailContract.View view;

    private String placeID;

    public PlaceDetailPresenterModule(PlaceDetailContract.View view, String placeID) {
        this.view = view;
        this.placeID = placeID;
    }

    @Provides
    PlaceDetailContract.View providePlaceDetailContractView() {
        return view;
    }

    @Provides
    @NonNull
    String providePlaceID() {
        return placeID;
    }

}
