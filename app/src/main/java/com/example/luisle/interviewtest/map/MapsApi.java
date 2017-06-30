package com.example.luisle.interviewtest.map;

import com.example.luisle.interviewtest.map.geocoding.Geocoding;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by LuisLe on 6/30/2017.
 */

public interface MapsApi {
    @GET("api/geocode/json?key=AIzaSyBfqWvbtt7yQkIZ8Xw3OobOBI5UTiEK-g0")
    Call<Geocoding> getPlaceLocation(@Query("address") String address);

//    @GET("api/directions/json?key=AIzaSyBfqWvbtt7yQkIZ8Xw3OobOBI5UTiEK-g0")
//    Call<Direction> getDirection(@Query("origin") String origin, @Query("destination") String destination);

}
