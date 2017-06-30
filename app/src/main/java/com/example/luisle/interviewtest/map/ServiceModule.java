package com.example.luisle.interviewtest.map;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by LuisLe on 6/30/2017.
 */

@Module
public class ServiceModule {

    private String baseUrl;

    public ServiceModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Singleton
    @Provides
    String provideBaseUrl() {
        return baseUrl;
    }

}
