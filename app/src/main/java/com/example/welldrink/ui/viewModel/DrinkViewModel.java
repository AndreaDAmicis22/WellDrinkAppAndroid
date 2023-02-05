package com.example.welldrink.ui.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.welldrink.data.repository.drink.IDrinkRepository;
import com.example.welldrink.model.Result;

public class DrinkViewModel extends ViewModel {

    private final IDrinkRepository drinkRepository;

    private MutableLiveData<Result> drinkMutableLiveData;

    private MutableLiveData<Result> randomDrinkLiveData;

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

    public MutableLiveData<Result> getDrinksRandomLiveData(){
        if(this.randomDrinkLiveData == null)
            setDrinkMutableLiveDataWithRandom();
        return this.randomDrinkLiveData;
    }

    public void getDrinksRandom(){
        this.setDrinkMutableLiveDataWithRandom();
    }

    private void setDrinkMutableLiveDataByName(String name){
        this.drinkMutableLiveData = this.drinkRepository.getDrinksByName(name);
    }

    private void setDrinkMutableLiveDataWithRandom(){
        this.randomDrinkLiveData = this.drinkRepository.getRandomDrink();
    }

}
