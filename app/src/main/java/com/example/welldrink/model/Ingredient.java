package com.example.welldrink.model;

public class Ingredient {

    private long id;
    private String name;
    private boolean alcohol;
    private int ABV;
    private String type;
    private String measure;

    public Ingredient(long id, String name, boolean alcohol, int abv, String type, String measure){
        this.id = id;
        this.name = name;
        this.alcohol = alcohol;
        this.ABV = abv;
        this.type = type;
        this.measure = measure;
    }

    public String getName() {
        return name;
    }

    public int getABV() {
        return ABV;
    }

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setABV(int ABV) {
        this.ABV = ABV;
    }

    public void setAlcohol(boolean alcohol) {
        this.alcohol = alcohol;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMeasure() {
        return measure;
    }
    public void setMeasure(String measure) {
        this.measure = measure;
    }
}
