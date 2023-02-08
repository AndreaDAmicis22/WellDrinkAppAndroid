package com.example.welldrink.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Drink {

    @SerializedName("ingredients")
    private List<Ingredient> ingredientList;
    private long id;
    private String name;
    private String instructions;
    @SerializedName("image")
    private String imageUrl;
    @SerializedName("glassName")
    private String glass;
    private String alcolType;
    @SerializedName("categoryName")
    private String category;
    private boolean favorite;

    public Drink(long id, String name, String instructions, List<Ingredient> ingredientList,
                 String imageUrl, String glass, String alcolType, String category) {
        this.id = id;
        this.name = name;
        this.instructions = instructions;
        this.ingredientList = ingredientList;
        this.imageUrl = imageUrl;
        this.glass = glass;
        this.alcolType = alcolType;
        this.category = category;
        this.favorite = false;
    }

    public Drink() {
        this.ingredientList = new ArrayList<>();
        this.name = "";
        this.instructions = "";
        this.favorite = false;
    }

    public Drink(String name, boolean favorite) {
        this.name = name;
        this.favorite = favorite;
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

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlcolType() {
        return alcolType;
    }

    public String getCategory() {
        return category;
    }

    public String getGlass() {
        return glass;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @NonNull
    @Override
    public String toString() {
        return "Drink{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", instructions='" + instructions + '\'' +
                ", ingredientList=" + ingredientList +
                ", imageUrl='" + imageUrl + '\'' +
                ", glass='" + glass + '\'' +
                ", alcolType='" + alcolType + '\'' +
                ", category='" + category + '\'' +
                ", favorite='" + isFavorite() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Drink)) return false;
        Drink drink = (Drink) o;
        return getId() == drink.getId() && Objects.equals(getName(), drink.getName()) && Objects.equals(getInstructions(), drink.getInstructions()) && Objects.equals(getIngredientList(), drink.getIngredientList()) && Objects.equals(getImageUrl(), drink.getImageUrl()) && Objects.equals(getGlass(), drink.getGlass()) && Objects.equals(getAlcolType(), drink.getAlcolType()) && Objects.equals(getCategory(), drink.getCategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getInstructions(), getIngredientList(), getImageUrl(), getGlass(), getAlcolType(), getCategory());
    }
}
