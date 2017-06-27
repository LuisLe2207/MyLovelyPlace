package com.example.luisle.interviewtest.data.source.di;

import com.example.luisle.interviewtest.AppModule;
import com.example.luisle.interviewtest.data.source.PlacesRepository;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by LuisLe on 6/27/2017.
 */


@Singleton
@Component(modules = {PlacesRepositoryModule.class, AppModule.class})
public interface PlacesRepositoryComponent {

    PlacesRepository getPlacesRepository();
}
