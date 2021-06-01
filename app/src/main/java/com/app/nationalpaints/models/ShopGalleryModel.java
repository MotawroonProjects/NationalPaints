package com.app.nationalpaints.models;

import java.io.Serializable;

public class ShopGalleryModel implements Serializable {
    private int id;
    private String title;
    private String desc;
    private String image;
    private String phone_number1;
    private String phone_number2;
    private int governorate_id;
    private int city_id;
    private String address;
    private String latitude;
    private String longitude;
    private String created_at;
    private String updated_at;
    private GovernmentModel.Data governorate;
    private AreaModel.Data city;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getImage() {
        return image;
    }

    public String getPhone_number1() {
        return phone_number1;
    }

    public String getPhone_number2() {
        return phone_number2;
    }

    public int getGovernorate_id() {
        return governorate_id;
    }

    public int getCity_id() {
        return city_id;
    }

    public String getAddress() {
        return address;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public GovernmentModel.Data getGovernorate() {
        return governorate;
    }

    public AreaModel.Data getCity() {
        return city;
    }
}