package com.example.luisle.interviewtest.map.geocoding;


/**
 * Created by LuisLe on 6/30/2017.
 */

public class Result {

    private Geometry geometry;

    private Result() {

    }

    public Result(Geometry geometry) {
        this.geometry = geometry;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }
}