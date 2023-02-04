package com.example.welldrink.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Drink {

    private long id;
    private String name;
    private String instructions;
    private List<Ingredient> ingredientList;
    @SerializedName("image")
    private String imageUrl;

    public Drink(long id, String name, String instructions, List<Ingredient> ingredientList, String imageUrl){
        this.id = id;
        this.name = name;
        this.instructions = instructions;
        this.ingredientList = ingredientList;
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @Override
    public String toString() {
        return "Drink{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", instructions='" + instructions + '\'' +
                ", ingredientList=" + ingredientList +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
