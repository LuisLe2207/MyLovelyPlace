package com.example.luisle.interviewtest.map;

import android.support.annotation.NonNull;

import com.example.luisle.interviewtest.map.geocoding.Geocoding;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LuisLe on 6/30/2017.
 */

public class Service implements ServiceContract{

    private Retrofit retrofit;

    @NonNull
    private String baseUrl;

    @Inject
    public Service(@NonNull String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Inject
    void initRetrofit() {
        if (!baseUrl.equals("https://maps.googleapis.com/maps/")) {
            baseUrl = "https://maps.googleapis.com/maps/";
        }
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void getLocation(@NonNull final String placeName , @NonNull String query, @NonNull final OnLocationLoaded callback) {
        MapsApi mapsApi = retrofit.create(MapsApi.class);
        Call<Geocoding> call = mapsApi.getPlaceLocation(query);
        call.enqueue(new Callback<Geocoding>() {
            @Override
            public void onResponse(Call<Geocoding> call, Response<Geocoding> response) {
                Geocoding geocoding = response.body();

                if (geocoding != null) {
                    final LatLng location = new LatLng(
                            geocoding.getResults().get(0).getGeometry().getLocation().getLat(),
                            geocoding.getResults().get(0).getGeometry().getLocation().getLng());

                    callback.onLoaded(location, placeName);
                }
            }

            @Override
            public void onFailure(Call<Geocoding> call, Throwable t) {
                callback.onFailed();
            }
        });
    }
}
