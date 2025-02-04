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

    private final MutableLiveData<Result> drinkMutableLiveData;

    private final MutableLiveData<Result> randomDrinkLiveData;

    private final MutableLiveData<Result> detailDrinkLiveData;

    private final MutableLiveData<Result> favoriteDrinksLiveData;

    private final MutableLiveData<Result> favoriteIngredientsLiveData;

    private final BaseDrinkRemoteDataSource drinkRemoteDataSource;
    private final BaseFavoriteDrinkDataSource baseFavoriteDrinksDataSource;

    public DrinkRepository(BaseDrinkRemoteDataSource drinkRemoteDataSource,
                           BaseFavoriteDrinkDataSource baseFavoriteDrinksDataSource) {
        this.drinkRemoteDataSource = drinkRemoteDataSource;
        this.drinkRemoteDataSource.setDrinkCallback(this);
        this.baseFavoriteDrinksDataSource = baseFavoriteDrinksDataSource;
        this.baseFavoriteDrinksDataSource.setDrinkResponseCallback(this);
        this.randomDrinkLiveData = new MutableLiveData<>();
        this.drinkMutableLiveData = new MutableLiveData<>();
        this.detailDrinkLiveData = new MutableLiveData<>();
        this.favoriteDrinksLiveData = new MutableLiveData<>(new Result.Success<Map<String, Drink>>(new HashMap<>()));
        this.favoriteIngredientsLiveData = new MutableLiveData<>();
        this.getFavoriteDrinks();
        this.getFavoriteIngredients();
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

    public MutableLiveData<Result> getRandomDrink() {
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

    @Override
    public MutableLiveData<Result> getFavoriteIngredient() {
        return this.favoriteIngredientsLiveData;
    }

    public MutableLiveData<Result> getFavoriteDrinksLiveData() {
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
    public void setDrinkUnfavorite(String name) {
        this.baseFavoriteDrinksDataSource.setDrinkUnFavorite(name);
    }

    @Override
    public void setIngredientFavorite(String name) {
        this.baseFavoriteDrinksDataSource.setIngredientFavorite(name);
    }

    @Override
    public void setIngredientUnfavorite(String name) {
        this.baseFavoriteDrinksDataSource.setIngredientUnfavorite(name);
    }

    @Override
    public void getFavoriteIngredients() {
        this.baseFavoriteDrinksDataSource.fetchIngredientFavorite();
    }

    @Override
    public void getFavoriteDrinks() {
        if (this.favoriteDrinksLiveData.getValue() != null) {
            Map<String, Drink> favorites = ((Result.Success<Map<String, Drink>>) this.favoriteDrinksLiveData.getValue()).getData();
            if (favorites.isEmpty())
                this.baseFavoriteDrinksDataSource.fetchDrinkFavorite();
        } else
            this.baseFavoriteDrinksDataSource.fetchDrinkFavorite();
    }

    public void forceFetchDrink(){
        this.baseFavoriteDrinksDataSource.fetchDrinkFavorite();
    }

    public Map<String, Drink> getFavoriteMap() {
        return ((Result.Success<Map<String, Drink>>) this.getFavoriteDrinksLiveData().getValue()).getData();
    }

    public void clearDrinkMutableLiveData() {
        if (this.drinkMutableLiveData != null)
            this.drinkMutableLiveData.setValue(new Result.Success<List<Drink>>(new ArrayList<Drink>()));
    }

    @Override
    public void onSuccessFromRemote(DrinkApiResponse drinkApiResponse) {
        Result result = new Result.Success<>(drinkApiResponse.getDrinkList());
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
        for (String s : favoriteList) {
            Log.d("RES", "success -> " + s);
            this.drinkRemoteDataSource.fetchFavoritesByName(s);
        }
    }

    @Override
    public void onSuccessFromFetchFavoriteRemote(DrinkApiResponse drinkApiResponse) {
        Log.d("RES", "---------> " + drinkApiResponse.getDrinkList().toString());
        Drink d = drinkApiResponse.getDrinkList().get(0);
        d.setFavorite(true);
        if (this.favoriteDrinksLiveData.getValue() != null) {
            Map<String, Drink> favorites = ((Result.Success<Map<String, Drink>>) this.favoriteDrinksLiveData.getValue()).getData();
            if (!favorites.containsKey(d.getName())) {
                favorites.put(d.getName(), d);
                this.favoriteDrinksLiveData.postValue(new Result.Success<>(favorites));
                Log.d("RES", "onSuccesFromFetchFavoriteRemote-> " + favorites.values().toString());
            }
            Log.d("RES", "---> " + favorites.values());
        } else {
            Log.d("RES", "ENTRATO NELL'ELSE");
            Map<String, Drink> fav = new HashMap<>();
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
        if (this.favoriteDrinksLiveData.getValue() != null) {
            Map<String, Drink> favorite = ((Result.Success<Map<String, Drink>>) this.favoriteDrinksLiveData.getValue()).getData();
            favorite.remove(name);
            Log.d("RES", "Removed");
        }
    }

    @Override
    public void onSuccessFromAddingFavoriteIngredient(String name) {
        if (this.favoriteIngredientsLiveData.getValue() != null) {
            List<String> favorites = ((Result.Success<List<String>>) this.favoriteIngredientsLiveData.getValue()).getData();
            int i = 0;
            while (i < favorites.size() && !favorites.get(i).equals(name))
                i++;
            if (i == favorites.size())
                favorites.add(name);
            this.favoriteIngredientsLiveData.postValue(new Result.Success<>(favorites));
        }
    }

    @Override
    public void onSuccessFromRemovingFavoriteIngredient(String name) {
        if (this.favoriteIngredientsLiveData.getValue() != null) {
            List<String> favorites = ((Result.Success<List<String>>) this.favoriteIngredientsLiveData.getValue()).getData();
            favorites.remove(name);
            this.favoriteIngredientsLiveData.postValue(new Result.Success<>(favorites));
        }
    }

    @Override
    public void onSuccessFromFetchFavoriteIngredients(List<String> name) {
        this.favoriteIngredientsLiveData.postValue(new Result.Success<>(name));
    }

    private void setDrinkIfFavorite(List<Drink> drinks) {
        for (Drink d : drinks)
            if (this.getFavoriteMap().containsKey(d.getName()))
                d.setFavorite(true);
    }
}
