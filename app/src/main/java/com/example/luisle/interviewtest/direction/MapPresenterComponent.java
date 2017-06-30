package com.example.luisle.interviewtest.direction;

import com.example.luisle.interviewtest.AppModule;
import com.example.luisle.interviewtest.data.source.di.PlacesRepositoryComponent;
import com.example.luisle.interviewtest.utils.FragmentScope;

import dagger.Component;

/**
 * Created by LuisLe on 6/30/2017.
 */
@FragmentScope
@Component(dependencies = PlacesRepositoryComponent.class, modules = {MapPresenterModule.class, AppModule.class})
public interface MapPresenterComponent {

    void inject(MapFragment fragment);

}
