package com.example.luisle.interviewtest.placedetail;

import com.example.luisle.interviewtest.data.source.di.PlacesRepositoryComponent;
import com.example.luisle.interviewtest.utils.FragmentScope;

import dagger.Component;

/**
 * Created by LuisLe on 6/28/2017.
 */
@FragmentScope
@Component(dependencies = PlacesRepositoryComponent.class, modules = PlaceDetailPresenterModule.class)
public interface PlaceDetailPresenterComponent {

    void inject(PlaceDetailFragment placeDetailFragment);

}
