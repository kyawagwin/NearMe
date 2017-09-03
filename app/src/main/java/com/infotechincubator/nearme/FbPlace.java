package com.infotechincubator.nearme;

import android.os.Parcel;
import android.os.Parcelable;

public class FbPlace implements Parcelable {
    String id;
    String name;
    int checkins;
    float overall_star_rating;
    String price_range;
    String picture_url;
    boolean is_silhouette;
    String category;
    double lat;
    double lng;


    public FbPlace(String name) {
        this.name = name;
    }

    protected FbPlace(Parcel in) {
        id = in.readString();
        name = in.readString();
        checkins = in.readInt();
        overall_star_rating = in.readFloat();
        price_range = in.readString();
        picture_url = in.readString();
        is_silhouette = in.readByte() != 0;
        category = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
    }

    public static final Creator<FbPlace> CREATOR = new Creator<FbPlace>() {
        @Override
        public FbPlace createFromParcel(Parcel in) {
            return new FbPlace(in);
        }

        @Override
        public FbPlace[] newArray(int size) {
            return new FbPlace[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeInt(checkins);
        parcel.writeFloat(overall_star_rating);
        parcel.writeString(price_range);
        parcel.writeString(picture_url);
        parcel.writeByte((byte) (is_silhouette ? 1 : 0));
        parcel.writeString(category);
        parcel.writeDouble(lat);
        parcel.writeDouble(lng);
    }
}
