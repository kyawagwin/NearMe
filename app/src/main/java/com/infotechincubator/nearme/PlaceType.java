package com.infotechincubator.nearme;

public class PlaceType {

    private String title;
    private int imageId;
    private String description;
    private String code;

    public PlaceType(String title, int imageId, String description, String code) {
        this.title = title;
        this.imageId = imageId;
        this.description = description;
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public int getImageId() {
        return imageId;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }
}
