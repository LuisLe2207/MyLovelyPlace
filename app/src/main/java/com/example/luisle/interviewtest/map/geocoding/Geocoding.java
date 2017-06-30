package com.example.luisle.interviewtest.map.geocoding;

import java.util.List;

/**
 * Created by LuisLe on 6/30/2017.
 */

public class Geocoding {

    private List<Result> results;
    private String status;

    public Geocoding() {
    }

    public Geocoding(List<Result> results, String status) {
        this.results = results;
        this.status = status;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
