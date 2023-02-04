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

    private final BaseDrinkRemoteDataSource drinkRemoteDataSource;

    public DrinkRepository(BaseDrinkRemoteDataSource drinkRemoteDataSource){
        this.drinkRemoteDataSource = drinkRemoteDataSource;
        this.drinkRemoteDataSource.setDrinkCallback(this);
    }

    @Override
    public MutableLiveData<Result> getDrinksByName(String name) {
        Log.d("API", "DrinkRepository->getDrinksByName");
        this.drinkRemoteDataSource.fetchDrinkByName(name);
        return this.drinkMutableLiveData;
    }

    @Override
    public void onSuccessFromRemote(DrinkApiResponse drinkApiResponse) {
        Result result = new Result.Success<List<Drink>>(drinkApiResponse.getDrinkList());
        drinkMutableLiveData.postValue(result);
        Log.d("API", "posted drink list");
    }
}
