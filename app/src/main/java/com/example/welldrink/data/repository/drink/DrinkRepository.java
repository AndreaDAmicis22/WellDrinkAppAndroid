package com.example.welldrink.data.repository.drink;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.welldrink.data.source.drinks.BaseDrinkRemoteDataSource;
import com.example.welldrink.data.source.drinks.BaseFavoriteDrinkDataSource;
import com.example.welldrink.model.Drink;
import com.example.welldrink.model.DrinkApiResponse;
import com.example.welldrink.model.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrinkRepository implements IDrinkRepository, IDrinkResponseCallback {

    private MutableLiveData<Result> drinkMutableLiveData;

    private MutableLiveData<Result> randomDrinkLiveData;

    private MutableLiveData<Result> detailDrinkLiveData;

    private MutableLiveData<Result> favoriteDrinksLiveData;

    private final BaseDrinkRemoteDataSource drinkRemoteDataSource;
    private final BaseFavoriteDrinkDataSource baseFavoriteDrinksDataSource;

    public DrinkRepository(BaseDrinkRemoteDataSource drinkRemoteDataSource,
                           BaseFavoriteDrinkDataSource baseFavoriteDrinksDataSource){
        this.drinkRemoteDataSource = drinkRemoteDataSource;
        this.drinkRemoteDataSource.setDrinkCallback(this);
        this.baseFavoriteDrinksDataSource = baseFavoriteDrinksDataSource;
        this.baseFavoriteDrinksDataSource.setDrinkResponseCallback(this);
        this.randomDrinkLiveData = new MutableLiveData<>();
        this.drinkMutableLiveData = new MutableLiveData<>();
        this.detailDrinkLiveData = new MutableLiveData<>();
        this.favoriteDrinksLiveData = new MutableLiveData<>(new Result.Success<Map<String, Drink>>(new HashMap<>()));
        this.getFavoriteDrinks();
        Log.e("LIKE", "COSTRUTTORE");
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
    ////??????
    }

    @Override
    public void setDrinkFavorite(String name) {
        this.baseFavoriteDrinksDataSource.setDrinkFavorite(name);
    }

    @Override
    public void setDrinkUnfavorite(String name) {
        this.baseFavoriteDrinksDataSource.setDrinkUnFavorite(name);
    }

    @Override
    public void getFavoriteDrinks() {
        if(this.favoriteDrinksLiveData.getValue() != null){
            Map<String, Drink> favorites = ((Result.Success<Map<String, Drink>>) this.favoriteDrinksLiveData.getValue()).getData();
            if(favorites.isEmpty())
                this.baseFavoriteDrinksDataSource.fetchDrinkFavorite();
        }else
            this.baseFavoriteDrinksDataSource.fetchDrinkFavorite();
    }

    public Map<String, Drink> getFavoriteMap(){
        return ((Result.Success<Map<String, Drink>>) this.getFavoriteDrinksLiveData().getValue()).getData();
    }

    public void clearDrinkMutableLiveData(){
        if(this.drinkMutableLiveData != null)
            this.drinkMutableLiveData.setValue(new Result.Success<List<Drink>>(new ArrayList<Drink>()));
    }

    @Override
    public void onSuccessFromRemote(DrinkApiResponse drinkApiResponse) {
        Result result = new Result.Success<List<Drink>>(drinkApiResponse.getDrinkList());
        Log.d("API", "onSuccessFromRemote");

        this.setDrinkIfFavorite(drinkApiResponse.getDrinkList());
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
        this.setDrinkIfFavorite(drinkApiREsponse.getDrinkList());
        this.randomDrinkLiveData.postValue(res);
    }

    @Override
    public void onSuccessFromRemoteDetails(DrinkApiResponse drinkApiResponse) {
        Result res = new Result.Success<>(drinkApiResponse.getDrinkList().get(0));
        this.setDrinkIfFavorite(drinkApiResponse.getDrinkList());
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
    public void onSuccessFromFetchFavoriteRemote(DrinkApiResponse drinkApiResponse) {
        Log.d("RES", "---------> " + drinkApiResponse.getDrinkList().toString());
        Drink d = drinkApiResponse.getDrinkList().get(0);
        d.setFevorite(true);
        if(this.favoriteDrinksLiveData.getValue() != null){
            Map<String, Drink> favorites = ((Result.Success<Map<String, Drink>>) this.favoriteDrinksLiveData.getValue()).getData();
            if(!favorites.containsKey(d.getName())){
                favorites.put(d.getName(), d);
                this.favoriteDrinksLiveData.postValue(new Result.Success<>(favorites));
                Log.d("RES", "onSuccesFromFetchFavoriteRemote-> " + favorites.values().toString());
            }
            Log.d("RES", "---> " + favorites.values());
        }else{
            Log.d("RES", "ENTRATO NELL'ELSE");
            Map<String, Drink> fav = new HashMap<String, Drink>();
            fav.put(d.getName(), d);
            Log.d("RES", fav.toString());
            Log.d("RES", fav.values().toString());
            this.favoriteDrinksLiveData.postValue(new Result.Success<>(fav));
        }
    }

    @Override
    public void onSuccessFromAddingFavorite(String name) {
        this.drinkRemoteDataSource.fetchFavoritesByName(name);
    }

    @Override
    public void onSuccessFromRemovingFavorite(String name) {
        if(this.favoriteDrinksLiveData.getValue() != null){
            Map<String, Drink> favorite = ((Result.Success<Map<String, Drink>>) this.favoriteDrinksLiveData.getValue()).getData();
            if(favorite.containsKey(name)){
                favorite.remove(name);
            }
            Log.d("RES", "Removed");
        }
    }

    private void setDrinkIfFavorite(List<Drink> drinks){
//        Log.d("LIKE", "-----setDrinkIfFavorite----- -> " + drinks.get(0).getName());
//        Log.d("LIKE", "FAVORITES " + this.getFavoriteMap().values().toString());
        for(Drink d : drinks){
            if(this.getFavoriteMap().containsKey(d.getName())){
                d.setFevorite(true);
            }
        }
//        Log.d("LIKE", "-----setDrinkIfFavorite----- -> " + drinks.get(0).isFavorite());
    }
}
