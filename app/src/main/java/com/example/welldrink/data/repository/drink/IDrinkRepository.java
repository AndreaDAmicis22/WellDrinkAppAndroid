package com.example.welldrink.data.repository.drink;

import androidx.lifecycle.MutableLiveData;

import com.example.welldrink.model.Drink;
import com.example.welldrink.model.Result;

import java.util.Map;

public interface IDrinkRepository {

    MutableLiveData<Result> getDrinksByName(String name);

    MutableLiveData<Result> getDrinksByIngredient(String ingredientName);

    MutableLiveData<Result> getDrinksByGlass(String glassName);

    MutableLiveData<Result> getDrinksByCategory(String category);

    MutableLiveData<Result> getRandomDrink();

    MutableLiveData<Result> getDrinkDetails(String name);

    MutableLiveData<Result> getTopDrinks();

    MutableLiveData<Result> getTopIngredients();

    MutableLiveData<Result> getMutableLiveData();

    MutableLiveData<Result> getFavoriteDrinksLiveData();

    MutableLiveData<Result> getIngredientsByName(String name);

    MutableLiveData<Result> getFavoriteIngredient();

    Map<String, Drink> getFavoriteMap();

    void getDrinkByNameFavorite(String name);

    void setDrinkFavorite(String name);

    void getFavoriteDrinks();

    void forceFetchDrink();

    void clearDrinkMutableLiveData();

    void setDrinkUnfavorite(String name);

    void setIngredientFavorite(String name);

    void setIngredientUnfavorite(String name);

    void getFavoriteIngredients();

}
