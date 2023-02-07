package com.example.welldrink.ui.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.welldrink.data.repository.drink.IDrinkRepository;
import com.example.welldrink.model.Drink;
import com.example.welldrink.model.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DrinkViewModel extends ViewModel {

    private final IDrinkRepository drinkRepository;

    private MutableLiveData<Result> drinkMutableLiveData;

    private MutableLiveData<Result> randomDrinkLiveData;

    private MutableLiveData<Result> detailsLiveData;

    private MutableLiveData<Result> favoritesLiveData; // non sono sicuro forse cambiare in un non livedata

    public DrinkViewModel(IDrinkRepository drinkRepository){
        this.drinkRepository = drinkRepository;
    }

    public MutableLiveData<Result> getDrinkMutableLiveData(){
        if(this.drinkMutableLiveData == null)
            this.drinkMutableLiveData = drinkRepository.getMutableLiveData();
        return this.drinkMutableLiveData;
    }

    public MutableLiveData<Result> getDrinksByNameLiveData(String name){
        if(this.drinkMutableLiveData == null)
            this.setDrinkMutableLiveDataByName(name);
        return this.drinkMutableLiveData;
    }

    public void getDrinksByName(String name){
        this.setDrinkMutableLiveDataByName(name);
    }

    public void getDrinksByIngredient(String ingredientName){
        this.setDrinkMutableLiveDataByIngredient(ingredientName);
    }

    public void getDrinksByGlass(String glass){
        this.setDrinkMutableLiveDataByGlass(glass);
    }

    public void getDrinksByCategory(String category){
        this.setDetailsLiveDataByCategory(category);
    }

    public MutableLiveData<Result> getDrinksRandomLiveData(){
        if(this.randomDrinkLiveData == null)
            setDrinkMutableLiveDataWithRandom();
        return this.randomDrinkLiveData;
    }

    public void getDrinksRandom(){
        this.setDrinkMutableLiveDataWithRandom();
    }

    public MutableLiveData<Result> getDrinkDetailsLiveData(String name){
        if(this.detailsLiveData == null)
            setDetailsLiveData(name);
        Log.d("RES", "GETLD !!!!");
        return this.detailsLiveData;
    }

    public void getDrinkDetail(String name){
        this.setDetailsLiveData(name);
    }

    public void clearDrinkDetails(){
        if(detailsLiveData != null)
            this.detailsLiveData.setValue(new Result.Success<Drink>(new Drink()));
    }

    public void clearDrinkMutableLiveData(){
        Log.d("RES", "ARRIVO QUI");
        if(this.drinkMutableLiveData != null){
//            this.drinkMutableLiveData.setValue(new Result.Success<Drink>(new Drink()));
            this.drinkMutableLiveData.setValue(new Result.Success<List<Drink>>(new ArrayList<>()));
            this.drinkRepository.clearDrinkMutableLiveData();
            this.drinkMutableLiveData = this.drinkRepository.getMutableLiveData();
//            Log.d("RES", "-------CLEAR----------");
//            Log.d("RES", ((Result.Success) this.drinkMutableLiveData.getValue()).getData().toString());
//            Log.d("RES", ((Result.Success) this.drinkRepository.getMutableLiveData().getValue()).getData().toString());

        }
    }

    public MutableLiveData<Result> getTopDrinksLiveData(){
        this.setDrinkMutableLiveDataTopDrinks();
        return this.drinkMutableLiveData;
    }

    public MutableLiveData<Result> getTopIngredientsLiveData(){
        this.setDrinkMutableLiveDataTopIngredients();
        return this.drinkMutableLiveData;
    }

    public void getIngredientsByName(String name){
        this.drinkMutableLiveData = this.drinkRepository.getIngredientsByName(name);
    }

    public void setDrinkFavorite(String name){
        this.drinkRepository.setDrinkFavorite(name);
    }

    public void setDrinkUnfavorite(String name){
        this.drinkRepository.setDrinkUnfavorite(name);
    }

    public void setIngredientFavorite(String name){
        this.drinkRepository.setIngredientFavorite(name);
    }

    public void setIngredientUnfavorite(String name){
        this.drinkRepository.setIngredientUnfavorite(name);
    }

    public boolean getFavoriteDrinks(){
        if(this.drinkRepository.getFavoriteDrinksLiveData().getValue() != null){
            Map<String, Drink> favorites = ((Result.Success<Map<String, Drink>>) this.drinkRepository.getFavoriteDrinksLiveData().getValue()).getData();
            if(favorites.isEmpty()){
                this.drinkRepository.getFavoriteDrinks();
                return true;
            }
            return false;
        }else{
            this.drinkRepository.getFavoriteDrinks();
            return true;
        }
    }

    public boolean getFavoriteIngredient(){
        if(this.drinkRepository.getFavoriteIngredient().getValue() != null){
            List<String> ingredients = ((Result.Success<List<String>>) this.drinkRepository.getFavoriteIngredient().getValue()).getData();
            if(ingredients.isEmpty()){
                this.drinkRepository.getFavoriteIngredients();
                return true;
            }
            return false;
        }else{
            this.drinkRepository.getFavoriteIngredients();
            return true;
        }
    }

    public MutableLiveData<Result> getFavoritesLiveData(){
        return this.drinkRepository.getFavoriteDrinksLiveData();
    }

    public MutableLiveData<Result> getFavoriteIngredientsLiveData(){
        return this.drinkRepository.getFavoriteIngredient();
    }

    public Map<String, Drink> getFavoriteMap(){
        return this.drinkRepository.getFavoriteMap();
    }

    private void setDrinkMutableLiveDataByName(String name){
        this.drinkMutableLiveData = this.drinkRepository.getDrinksByName(name);
    }

    private void setDrinkMutableLiveDataWithRandom(){
        this.randomDrinkLiveData = this.drinkRepository.getRandomDrink();
    }

    private void setDetailsLiveData(String name){
        this.detailsLiveData = this.drinkRepository.getDrinkDetails(name);
    }

    private void setDrinkMutableLiveDataByIngredient(String ingredient){
        this.drinkMutableLiveData = this.drinkRepository.getDrinksByIngredient(ingredient);
    }

    private void setDrinkMutableLiveDataByGlass(String glassName){
        this.drinkMutableLiveData = this.drinkRepository.getDrinksByGlass(glassName);
    }

    private void setDetailsLiveDataByCategory(String category){
        this.drinkMutableLiveData = this.drinkRepository.getDrinksByCategory(category);
    }

    private void setDrinkMutableLiveDataTopDrinks(){
        this.drinkMutableLiveData = this.drinkRepository.getTopDrinks();
    }

    private void setDrinkMutableLiveDataTopIngredients(){
        this.drinkMutableLiveData = this.drinkRepository.getTopIngredients();
    }

}
