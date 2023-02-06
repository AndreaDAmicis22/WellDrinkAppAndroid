package com.example.welldrink.data.repository.drink;

import androidx.lifecycle.MutableLiveData;

import com.example.welldrink.model.Result;

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

    MutableLiveData<Result> getIngredientsByName(String name);

    void clearDrinkMutableLiveData();
}
