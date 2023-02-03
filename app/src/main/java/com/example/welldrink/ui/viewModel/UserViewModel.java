package com.example.welldrink.ui.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.welldrink.data.repository.user.IUserRepository;
import com.example.welldrink.model.Result;

public class UserViewModel extends ViewModel {

    private final IUserRepository userRepository;

    private MutableLiveData<Result> userMutableLiveData;

    public UserViewModel(IUserRepository userRepository){
        this.userRepository = userRepository;
    }

    public MutableLiveData<Result> getUserMutableLiveData(String email, String password, boolean isLogIn){
        if(userMutableLiveData == null){
            setUserMutableLiveData(email, password, isLogIn);
        }
        return userMutableLiveData;
    }

    private void setUserMutableLiveData(String email, String password, boolean isLogIn){
        this.userMutableLiveData = this.userRepository.getUser(email, password, isLogIn);
    }

}
