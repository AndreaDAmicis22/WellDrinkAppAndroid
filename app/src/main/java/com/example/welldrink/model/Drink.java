package com.example.welldrink.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Drink {

    private long id;
    private String name;
    private String instructions;
    @SerializedName("ingredients")
    private List<Ingredient> ingredientList;
    @SerializedName("image")
    private String imageUrl;
    @SerializedName("glassName")
    private String glass;
    private String alcolType;
    @SerializedName("categoryName")
    private String category;
    private boolean fevorite;

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
        this.fevorite = false;
    }

    public Drink(){
        this.ingredientList = new ArrayList<Ingredient>();
        this.name = "";
        this.instructions = "";
        this.fevorite = false;
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

    public String getAlcolType() {
        return alcolType;
    }

    public String getCategory() {
        return category;
    }

    public String getGlass() {
        return glass;
    }

    public void setAlcolType(String alcolType) {
        this.alcolType = alcolType;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setGlass(String glass) {
        this.glass = glass;
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
                ", glass='" + glass + '\'' +
                ", alcolType='" + alcolType + '\'' +
                ", category='" + category + '\'' +
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
