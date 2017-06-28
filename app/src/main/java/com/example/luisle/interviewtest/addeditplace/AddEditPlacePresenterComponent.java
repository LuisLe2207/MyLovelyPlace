package com.example.luisle.interviewtest.addeditplace;

import com.example.luisle.interviewtest.data.source.di.PlacesRepositoryComponent;
import com.example.luisle.interviewtest.utils.FragmentScope;

import dagger.Component;

/**
 * Created by LuisLe on 6/28/2017.
 */
//
@FragmentScope
@Component(dependencies = PlacesRepositoryComponent.class, modules = AddEditPlacePresenterModule.class)
public interface AddEditPlacePresenterComponent {

    void inject(AddEditPlaceFragment fragment);
}
