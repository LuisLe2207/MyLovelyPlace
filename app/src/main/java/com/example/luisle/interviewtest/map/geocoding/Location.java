package com.example.luisle.interviewtest.map.geocoding;

/**
 * Created by LuisLe on 6/30/2017.
 */

public class Location {

    private double lat;
    private double lng;

    private Location() {

    }

    public Location(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
