package com.example.welldrink.data.source.drinks;


import com.example.welldrink.data.repository.drink.IDrinkResponseCallback;
import com.example.welldrink.model.Drink;

public abstract class BaseDrinkRemoteDataSource {

    protected IDrinkResponseCallback drinkCallback;

    public void setDrinkCallback(IDrinkResponseCallback drinkCallback) {
        this.drinkCallback = drinkCallback;
    }

    public abstract void fetchDrinkByName(String name);

    public abstract void fetchRandomDrink();

    public abstract void fetchDrinkDetail(String name);

    public abstract void fetchDrinkByIngredient(String ingredientName);

    public abstract void fetchDrinkByGlass(String glassName);

    public abstract void fetchDrinkByCategory(String category);

    public abstract void fetchTopDrinks();

    public abstract void fetchTopIngredients();

    public abstract void fetchIngredientsByName(String ingredientName);

}
