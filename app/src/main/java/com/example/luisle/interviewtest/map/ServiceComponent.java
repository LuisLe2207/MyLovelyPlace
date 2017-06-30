package com.example.luisle.interviewtest.map;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by LuisLe on 6/30/2017.
 */

@Singleton
@Component(modules = ServiceModule.class)
public interface ServiceComponent {

    Service getService();

}
