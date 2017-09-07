package com.infotechincubator.nearme;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

public class Place implements Parcelable {
    private String placeId;
    private String source;
    private LatLng latLng;
    private float rating;
    private int priceLevel;
    private String name;
    private String address;
    private String phoneNumber;
    private String iconUrl;
    private int distance;

    public Place(String placeId, String source, LatLng latLng, float rating, int priceLevel, String name, String address, String phoneNumber, String iconUrl) {
        this.placeId = placeId;
        this.source = source;
        this.latLng = latLng;
        this.rating = rating;
        this.priceLevel = priceLevel;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.iconUrl = iconUrl;
    }

    protected Place(Parcel in) {
        placeId = in.readString();
        source = in.readString();
        latLng = in.readParcelable(LatLng.class.getClassLoader());
        rating = in.readFloat();
        priceLevel = in.readInt();
        name = in.readString();
        address = in.readString();
        phoneNumber = in.readString();
        iconUrl = in.readString();
        distance = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(placeId);
        dest.writeString(source);
        dest.writeParcelable(latLng, flags);
        dest.writeFloat(rating);
        dest.writeInt(priceLevel);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(phoneNumber);
        dest.writeString(iconUrl);
        dest.writeInt(distance);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

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

    public String getIconUrl() {
        return iconUrl;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

}
