package com.example.luisle.interviewtest.data.source.di;

import android.content.Context;

import com.example.luisle.interviewtest.data.source.db.IPlacesDataSource;
import com.example.luisle.interviewtest.data.source.db.PlacesDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by LuisLe on 6/27/2017.
 */

@Module
public class PlacesRepositoryModule {

    @Singleton
    @Provides
    IPlacesDataSource providePlacesDataSource(Context context) {
        return new PlacesDataSource(context);
    }
}
