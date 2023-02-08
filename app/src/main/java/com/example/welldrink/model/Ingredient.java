package com.example.welldrink.model;

import com.google.gson.annotations.SerializedName;

public class Ingredient {

    @SerializedName("ingredient")
    private String name;
    private long id;
    private int ABV;
    private String type;
    private String measure;

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMeasure() {
        return measure;
    }

}
