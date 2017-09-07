package com.infotechincubator.nearme;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

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
    List<SocialId> socialIds;

    public FbPlace(String name) {
        this.name = name;
        this.socialIds = new ArrayList<>();
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
        socialIds = in.createTypedArrayList(SocialId.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeInt(checkins);
        dest.writeFloat(overall_star_rating);
        dest.writeString(price_range);
        dest.writeString(picture_url);
        dest.writeByte((byte) (is_silhouette ? 1 : 0));
        dest.writeString(category);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
        dest.writeTypedList(socialIds);
    }

    @Override
    public int describeContents() {
        return 0;
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
}
