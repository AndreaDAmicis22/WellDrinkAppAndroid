package com.example.welldrink.data.repository.drink;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.welldrink.data.source.drinks.BaseDrinkRemoteDataSource;
import com.example.welldrink.model.Drink;
import com.example.welldrink.model.DrinkApiResponse;
import com.example.welldrink.model.Result;

import java.util.List;

public class DrinkRepository implements IDrinkRepository, IDrinkResponseCallback {

    private MutableLiveData<Result> drinkMutableLiveData;

    private MutableLiveData<Result> randomDrinkLiveData;

    private final BaseDrinkRemoteDataSource drinkRemoteDataSource;

    public DrinkRepository(BaseDrinkRemoteDataSource drinkRemoteDataSource){
        this.drinkRemoteDataSource = drinkRemoteDataSource;
        this.drinkRemoteDataSource.setDrinkCallback(this);
        this.randomDrinkLiveData = new MutableLiveData<>();
        this.drinkMutableLiveData = new MutableLiveData<>();
    }

    @Override
    public MutableLiveData<Result> getDrinksByName(String name) {
        Log.d("API", "DrinkRepository->getDrinksByName");
        this.drinkRemoteDataSource.fetchDrinkByName(name);
        return this.drinkMutableLiveData;
    }

    public MutableLiveData<Result> getRandomDrink(){
        drinkRemoteDataSource.fetchRandomDrink();
        return this.randomDrinkLiveData;
    }

    @Override
    public void onSuccessFromRemote(DrinkApiResponse drinkApiResponse) {
        Result result = new Result.Success<List<Drink>>(drinkApiResponse.getDrinkList());
        drinkMutableLiveData.postValue(result);
        Log.d("API", "posted drink list");
    }

    @Override
    public void onFailureFromRemote(String message) {
        Result result = new Result.Error(message);
        drinkMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessFromRemoteRandom(DrinkApiResponse drinkApiREsponse) {
        Result res = new Result.Success<>(drinkApiREsponse.getDrinkList().get(0));
        randomDrinkLiveData.postValue(res);
    }
}
