package com.infotechincubator.nearme;

import android.os.Parcel;
import android.os.Parcelable;

public class SocialId implements Parcelable {
    private String name;
    private String id;

    protected SocialId(Parcel in) {
        name = in.readString();
        id = in.readString();
    }

    public static final Creator<SocialId> CREATOR = new Creator<SocialId>() {
        @Override
        public SocialId createFromParcel(Parcel in) {
            return new SocialId(in);
        }

        @Override
        public SocialId[] newArray(int size) {
            return new SocialId[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SocialId(String name, String id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(id);
    }
}
