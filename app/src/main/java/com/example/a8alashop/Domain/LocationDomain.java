package com.example.a8alashop.Domain;

public class LocationDomain {
    private int id;
    private String loc;

    @Override
    public String toString() {
        return  loc ;
    }

    public LocationDomain() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }


}
