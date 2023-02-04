package com.example.welldrink.ui.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.welldrink.data.repository.drink.IDrinkRepository;
import com.example.welldrink.model.Result;

public class DrinkViewModel extends ViewModel {

    private final IDrinkRepository drinkRepository;

    private MutableLiveData<Result> drinkMutableLiveData;

    public DrinkViewModel(IDrinkRepository drinkRepository){
        this.drinkRepository = drinkRepository;
    }




    private void setDrinkMutableLiveData(String name){
        this.drinkMutableLiveData = drinkRepository.getDrinksByName(name);
    }

}
