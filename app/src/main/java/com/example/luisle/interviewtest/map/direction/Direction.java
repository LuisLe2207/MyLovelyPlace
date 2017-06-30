package com.example.luisle.interviewtest.map.direction;

import java.util.List;

/**
 * Created by LuisLe on 5/25/2017.
 */

public class Direction {
    private List<Routes> routes;

    public List<Routes> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Routes> routes) {
        this.routes = routes;
    }

    public Direction(List<Routes> routes) {
        this.routes = routes;
    }
}
