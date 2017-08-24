package com.infotechincubator.nearme;

import com.google.android.gms.maps.model.LatLng;

public class Place {
    private String placeId;
    private String source;
    private LatLng latLng;
    private float rating;
    private int priceLevel;
    private String name;
    private String address;
    private String phoneNumber;

    public Place(String placeId, String source, LatLng latLng, float rating, int priceLevel, String name, String address, String phoneNumber) {
        this.placeId = placeId;
        this.source = source;
        this.latLng = latLng;
        this.rating = rating;
        this.priceLevel = priceLevel;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getPlaceId() {
        return placeId;
    }

    public String getSource() {
        return source;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public float getRating() {
        return rating;
    }

    public int getPriceLevel() {
        return priceLevel;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
