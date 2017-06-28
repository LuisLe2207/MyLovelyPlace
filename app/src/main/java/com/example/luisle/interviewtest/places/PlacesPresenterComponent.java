package com.example.luisle.interviewtest.places;

import com.example.luisle.interviewtest.MainActivity;
import com.example.luisle.interviewtest.data.source.di.PlacesRepositoryComponent;
import com.example.luisle.interviewtest.utils.FragmentScope;

import dagger.Component;

/**
 * Created by LuisLe on 6/28/2017.
 */

@FragmentScope
@Component(dependencies = PlacesRepositoryComponent.class, modules = PlacesPresenterModule.class)
public interface PlacesPresenterComponent {

    void inject(MainActivity activity);

}
