package com.example.welldrink.data.repository.drink;

import androidx.lifecycle.MutableLiveData;

import com.example.welldrink.model.Result;

public interface IDrinkRepository {

    MutableLiveData<Result> getDrinksByName(String name);

    MutableLiveData<Result> getRandomDrink();

    MutableLiveData<Result> getDrinkDetails(String name);

    MutableLiveData<Result> getMutableLiveData();

}
