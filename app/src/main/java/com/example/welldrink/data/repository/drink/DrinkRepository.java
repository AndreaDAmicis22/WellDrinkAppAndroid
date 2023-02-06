package com.example.welldrink.data.repository.drink;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.welldrink.data.source.drinks.BaseDrinkRemoteDataSource;
import com.example.welldrink.data.source.drinks.BaseFavoriteDrinksDataSource;
import com.example.welldrink.model.Drink;
import com.example.welldrink.model.DrinkApiResponse;
import com.example.welldrink.model.Result;

import java.util.ArrayList;
import java.util.List;

public class DrinkRepository implements IDrinkRepository, IDrinkResponseCallback {

    private MutableLiveData<Result> drinkMutableLiveData;

    private MutableLiveData<Result> randomDrinkLiveData;

    private MutableLiveData<Result> detailDrinkLiveData;

    private MutableLiveData<Result> favoriteDrinksLiveData;

    private final BaseDrinkRemoteDataSource drinkRemoteDataSource;
    private final BaseFavoriteDrinksDataSource baseFavoriteDrinksDataSource;

    public DrinkRepository(BaseDrinkRemoteDataSource drinkRemoteDataSource,
                           BaseFavoriteDrinksDataSource baseFavoriteDrinksDataSource){
        this.drinkRemoteDataSource = drinkRemoteDataSource;
        this.drinkRemoteDataSource.setDrinkCallback(this);
        this.baseFavoriteDrinksDataSource = baseFavoriteDrinksDataSource;
        this.baseFavoriteDrinksDataSource.setDrinkResponseCallback(this);
        this.randomDrinkLiveData = new MutableLiveData<>();
        this.drinkMutableLiveData = new MutableLiveData<>();
        this.detailDrinkLiveData = new MutableLiveData<>();
        this.favoriteDrinksLiveData = new MutableLiveData<>();
    }

    @Override
    public MutableLiveData<Result> getDrinksByName(String name) {
        this.drinkRemoteDataSource.fetchDrinkByName(name);
        return this.drinkMutableLiveData;
    }

    @Override
    public MutableLiveData<Result> getDrinksByIngredient(String ingredientName) {
        this.drinkRemoteDataSource.fetchDrinkByIngredient(ingredientName);
        return this.drinkMutableLiveData;
    }

    @Override
    public MutableLiveData<Result> getDrinksByGlass(String glassName) {
        this.drinkRemoteDataSource.fetchDrinkByGlass(glassName);
        return this.drinkMutableLiveData;
    }

    @Override
    public MutableLiveData<Result> getDrinksByCategory(String category) {
        this.drinkRemoteDataSource.fetchDrinkByCategory(category);
        return this.drinkMutableLiveData;
    }

    public MutableLiveData<Result> getRandomDrink(){
        drinkRemoteDataSource.fetchRandomDrink();
        return this.randomDrinkLiveData;
    }

    @Override
    public MutableLiveData<Result> getDrinkDetails(String name) {
        this.drinkRemoteDataSource.fetchDrinkDetail(name);
        return this.detailDrinkLiveData;
    }

    @Override
    public MutableLiveData<Result> getTopDrinks() {
        this.drinkRemoteDataSource.fetchTopDrinks();
        return this.detailDrinkLiveData;
    }

    @Override
    public MutableLiveData<Result> getTopIngredients() {
        this.drinkRemoteDataSource.fetchTopIngredients();
        return this.detailDrinkLiveData;
    }

    @Override
    public MutableLiveData<Result> getMutableLiveData() {
        return this.drinkMutableLiveData;
    }

    @Override
    public MutableLiveData<Result> getIngredientsByName(String name) {
        this.drinkRemoteDataSource.fetchIngredientsByName(name);
        return this.drinkMutableLiveData;
    }

    public MutableLiveData<Result> getFavoriteDrinksLiveData(){
        return this.favoriteDrinksLiveData;
    }

    @Override
    public void getDrinkByNameFavorite(String name) {

    }

    @Override
    public void setDrinkFavorite(String name) {
        this.baseFavoriteDrinksDataSource.setDrinkFavorite(name);
    }

    @Override
    public void getFavoriteDrinks() {
        if(this.favoriteDrinksLiveData.getValue() != null){
            List<Drink> favorites = ((Result.Success<List<Drink>>) this.favoriteDrinksLiveData.getValue()).getData();
            if(favorites.isEmpty())
                this.baseFavoriteDrinksDataSource.fetchDrinkFavorite();
        }else
            this.baseFavoriteDrinksDataSource.fetchDrinkFavorite();
    }

    public void clearDrinkMutableLiveData(){
        if(this.drinkMutableLiveData != null)
            this.drinkMutableLiveData.setValue(new Result.Success<List<Drink>>(new ArrayList<Drink>()));
    }

    @Override
    public void onSuccessFromRemote(DrinkApiResponse drinkApiResponse) {
        Result result = new Result.Success<List<Drink>>(drinkApiResponse.getDrinkList());
        Log.d("API", "onSuccessFromRemote");
        this.drinkMutableLiveData.postValue(result);
    }

    @Override
    public void onFailureFromRemote(String message) {
        Result result = new Result.Error(message);
        this.drinkMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessFromRemoteRandom(DrinkApiResponse drinkApiREsponse) {
        Result res = new Result.Success<>(drinkApiREsponse.getDrinkList().get(0));
        this.randomDrinkLiveData.postValue(res);
    }

    @Override
    public void onSuccessFromRemoteDetails(DrinkApiResponse drinkApiResponse) {
        Result res = new Result.Success<>(drinkApiResponse.getDrinkList().get(0));
        this.detailDrinkLiveData.postValue(res);
    }

    @Override
    public void onSuccessFromFetchFavorite(List<String> favoriteList) {
        for(String s : favoriteList){
            Log.d("RES", "success -> " + s);
            //fetch of all drinks by name
            this.drinkRemoteDataSource.fetchFavoritesByName(s);
        }
    }

    @Override
    public void onSuccesFromFetchFavoriteRemote(DrinkApiResponse drinkApiResponse) {
        if(this.favoriteDrinksLiveData.getValue() != null){
            List<Drink> favorites = ((Result.Success<List<Drink>>) this.favoriteDrinksLiveData.getValue()).getData();
            Drink newDrink = drinkApiResponse.getDrinkList().get(0);
            boolean isInside = false;
            for(Drink d : favorites){
                Log.d("RES", "onSuccesFromFetchFavoriteRemote -> " + d.getName());
                if(d.getName().equals(newDrink.getName()))
                    isInside = true;
            }
            if(!isInside){
                favorites.add(newDrink);
                Log.d("RES", "onSuccesFromFetchFavoriteRemote ADDED");
                Log.d("RES", favorites.toString());
                this.favoriteDrinksLiveData.postValue(new Result.Success<>(favorites));
            }
        }else{
            this.favoriteDrinksLiveData.postValue(new Result.Success<>(drinkApiResponse.getDrinkList()));
        }

    }
}
