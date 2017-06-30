package com.example.luisle.interviewtest.map.geocoding;

/**
 * Created by LuisLe on 6/30/2017.
 */

public class Geometry {
    private Location location;

    public Geometry() {

    }

    public Geometry(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
