package com.example.welldrink.ui.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.welldrink.data.repository.drink.IDrinkRepository;

public class DrinkViewModelFactory implements ViewModelProvider.Factory {

    private final IDrinkRepository drinkRepository;

    public DrinkViewModelFactory(IDrinkRepository drinkRepository) {
        this.drinkRepository = drinkRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DrinkViewModel(drinkRepository);
    }


}
