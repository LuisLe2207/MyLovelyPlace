package com.example.luisle.interviewtest;

import com.example.luisle.interviewtest.direction.MapPresenter;
import com.example.luisle.interviewtest.map.ServiceComponent;
import com.example.luisle.interviewtest.map.ServiceModule;
import com.example.luisle.interviewtest.placedetail.PlaceDetailPresenter;
import com.example.luisle.interviewtest.utils.PresenterScope;

import dagger.Component;

/**
 * Created by LuisLe on 6/30/2017.
 */

@PresenterScope
@Component(dependencies = ServiceComponent.class, modules = ServiceModule.class)
public interface AppComponent {

    void inject(PlaceDetailPresenter  presenter);
    void inject(MapPresenter presenter);
}
