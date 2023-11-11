package ru.korobeynikov.myweatherapplication.json;

import com.google.gson.annotations.SerializedName;

public class Sys {

    @SerializedName("type")
    private int type;

    @SerializedName("id")
    private int id;

    @SerializedName("country")
    private String country;

    @SerializedName("sunrise")
    private int sunrise;

    @SerializedName("sunset")
    private int sunset;

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public int getSunrise() {
        return sunrise;
    }

    public int getSunset() {
        return sunset;
    }
}