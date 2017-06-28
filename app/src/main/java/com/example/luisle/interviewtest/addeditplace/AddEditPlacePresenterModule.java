package com.example.luisle.interviewtest.addeditplace;

import android.support.annotation.Nullable;

import dagger.Module;
import dagger.Provides;

/**
 * Created by LuisLe on 6/28/2017.
 */
@Module
public class AddEditPlacePresenterModule {

    private final AddEditPlaceContract.View view;

    private String placeID;

    public AddEditPlacePresenterModule(AddEditPlaceContract.View view, String placeID) {
        this.view = view;
        this.placeID = placeID;
    }

    @Provides
    AddEditPlaceContract.View provideAddEditPlaceContractView() {
        return view;
    }

    @Provides
    @Nullable
    String providePlaceID() {
        return placeID;
    }
}
