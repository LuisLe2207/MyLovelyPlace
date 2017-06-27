package com.example.luisle.interviewtest;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by LuisLe on 6/27/2017.
 */

@Module
public final class AppModule {

    private final Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    Context provideContext() {
        return context;
    }
}
