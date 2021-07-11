package com.project.coronavirustracker.models;

public class LocationStats {
    private String state;
    private String country;
    private int latestTotalCases;
    private int diffFromPrevDev;

    public String getState() {
        return state;
    }


    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLatestTotalCases() {
        return latestTotalCases;
    }


    public int getDiffFromPrevDev() {
        return diffFromPrevDev;
    }

    public void setDiffFromPrevDev(int diffFromPrevDev) {
        this.diffFromPrevDev = diffFromPrevDev;
    }


    public void setLatestTotalCases(int latestTotalCases) {
        this.latestTotalCases = latestTotalCases;
    }



    public LocationStats() {
    }

    @Override
    public String toString() {
        return "LocationStats{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", latestTotalCases=" + latestTotalCases +
                ", diffFromPrevDev=" + diffFromPrevDev +
                '}';
    }

    public LocationStats(String state, String country, int latestTotalCases, int diffFromPrevDev) {
        this.state = state;
        this.country = country;
        this.latestTotalCases = latestTotalCases;
        this.diffFromPrevDev = diffFromPrevDev;
    }
}
