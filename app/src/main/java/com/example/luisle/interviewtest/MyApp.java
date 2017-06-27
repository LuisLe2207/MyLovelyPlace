package com.example.luisle.interviewtest;

import android.app.Application;

import com.example.luisle.interviewtest.data.source.di.DaggerPlacesRepositoryComponent;
import com.example.luisle.interviewtest.data.source.di.PlacesRepositoryComponent;

/**
 * Created by LuisLe on 6/27/2017.
 */

public class MyApp extends Application {

    private PlacesRepositoryComponent repositoryComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        repositoryComponent = DaggerPlacesRepositoryComponent.builder()
                .appModule(new AppModule((getApplicationContext())))
                .build();

    }

    public PlacesRepositoryComponent getRepositoryComponent() {
        return repositoryComponent;
    }
}
