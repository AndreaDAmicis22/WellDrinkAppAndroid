package com.example.welldrink.ui.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.welldrink.data.repository.user.IUserRepository;
import com.example.welldrink.model.Result;
import com.example.welldrink.model.User;

public class UserViewModel extends ViewModel {

    private final IUserRepository userRepository;
    private MutableLiveData<Result> userMutableLiveData;
    private boolean authError;

    public UserViewModel(IUserRepository userRepository){
        this.userRepository = userRepository;
        this.authError = false;
    }

    public MutableLiveData<Result> getUserMutableLiveData(String email, String password, boolean isLogIn){
        if(userMutableLiveData == null){
            setUserMutableLiveData(email, password, isLogIn);
        }
        return userMutableLiveData;
    }

    public void getUser(String email, String password, boolean isLogin){
        setUserMutableLiveData(email, password, isLogin);
    }

    public User getLoggedUser(){
        return this.userRepository.getLoggedUser();
    }

    public void logOut(){
        this.userRepository.logOut();
    }

    private void setUserMutableLiveData(String email, String password, boolean isLogIn){
        Log.d("AUTH", "setUserMutableLiveData");
        this.userMutableLiveData = this.userRepository.getUser(email, password, isLogIn);
    }

    public boolean isAuthError(){
        return this.authError;
    }

    public void setAuthError(boolean authError) {
        this.authError = authError;
    }
}
