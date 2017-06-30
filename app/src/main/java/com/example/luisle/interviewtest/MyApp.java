package com.example.luisle.interviewtest;

import android.app.Application;

import com.example.luisle.interviewtest.data.source.di.DaggerPlacesRepositoryComponent;
import com.example.luisle.interviewtest.data.source.di.PlacesRepositoryComponent;
import com.example.luisle.interviewtest.data.source.di.PlacesRepositoryModule;
import com.example.luisle.interviewtest.map.DaggerServiceComponent;
import com.example.luisle.interviewtest.map.ServiceComponent;
import com.example.luisle.interviewtest.map.ServiceModule;

/**
 * Created by LuisLe on 6/27/2017.
 */

public class MyApp extends Application {

    private PlacesRepositoryComponent repositoryComponent;

    private ServiceComponent serviceComponent;

    private static final String baseUrl = "https://maps.googleapis.com/maps/";

    @Override
    public void onCreate() {
        super.onCreate();

        repositoryComponent = DaggerPlacesRepositoryComponent.builder()
                .placesRepositoryModule(new PlacesRepositoryModule())
                .appModule(new AppModule((getApplicationContext())))
                .build();

        serviceComponent = DaggerServiceComponent.builder()
                .serviceModule(new ServiceModule(baseUrl))
                .build();

    }

    public PlacesRepositoryComponent getRepositoryComponent() {
        return repositoryComponent;
    }

    public ServiceComponent getServiceComponent() {
        return serviceComponent;
    }
}
