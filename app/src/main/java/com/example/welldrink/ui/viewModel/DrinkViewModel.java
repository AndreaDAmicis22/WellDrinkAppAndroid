package com.example.welldrink.ui.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.welldrink.data.repository.drink.IDrinkRepository;
import com.example.welldrink.model.Result;

public class DrinkViewModel extends ViewModel {

    private final IDrinkRepository drinkRepository;

    private MutableLiveData<Result> drinkMutableLiveData;

    private MutableLiveData<Result> randomDrinkLiveData;

    private MutableLiveData<Result> detailsLiveData;

    public DrinkViewModel(IDrinkRepository drinkRepository){
        this.drinkRepository = drinkRepository;
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
        return this.detailsLiveData;
    }

    public void getDrinkDetail(String name){
        this.setDetailsLiveData(name);
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

}
