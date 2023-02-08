package com.example.welldrink.data.repository.user;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.welldrink.data.source.user.BaseUserAuthenticationRemoteDataSource;
import com.example.welldrink.data.source.user.BaseUserRemoteDataSource;
import com.example.welldrink.model.Result;
import com.example.welldrink.model.User;

public class UserRepository implements IUserRepository, UserResponseCallback {

    private final BaseUserRemoteDataSource userRemoteDataSource;
    private final BaseUserAuthenticationRemoteDataSource userAuthenticationRemoteDataSource;
    private final MutableLiveData<Result> userMutableLiveData;

    public UserRepository(BaseUserRemoteDataSource userRemoteDataSource,
                          BaseUserAuthenticationRemoteDataSource userAuthenticationRemoteDataSource) {
        this.userRemoteDataSource = userRemoteDataSource;
        this.userAuthenticationRemoteDataSource = userAuthenticationRemoteDataSource;
        this.userRemoteDataSource.setUserResponseCallback(this);
        this.userAuthenticationRemoteDataSource.setUserResponseCallback(this);
        this.userMutableLiveData = new MutableLiveData<>();
    }


    @Override
    public MutableLiveData<Result> getUser(String email, String password, boolean isLogIn, String username) {
        if (isLogIn) {
            this.logIn(email, password);
        } else {
            this.signUp(email, password, username);
        }
        return this.userMutableLiveData;
    }

    @Override
    public void signUp(String mail, String password, String username) {
        userAuthenticationRemoteDataSource.signUp(mail, password, username);
    }

    @Override
    public void logIn(String mail, String password) {
        userAuthenticationRemoteDataSource.signIn(mail, password);
    }

    @Override
    public User getLoggedUser() {
        return userAuthenticationRemoteDataSource.getLoggedUser();
    }

    @Override
    public void logOut() {
        this.userAuthenticationRemoteDataSource.signOut();
    }

    @Override
    public void onSuccessFromAuthentication(User user) {
        if (user != null) {
            userRemoteDataSource.saveUser(user);
        }
    }

    @Override
    public void onSuccessFromRemoteDatabase(User user) {
        Result result = new Result.Success<>(user);
        userMutableLiveData.postValue(result);
        Log.d("AUTH", "onSuccessFromRemoteDatabase -> posted");
    }

    @Override
    public void onFailureFromRemoteDatabase(String message) {
        Result result = new Result.Error(message);
        userMutableLiveData.postValue(result);
    }

    @Override
    public void onFailureFromAuthentication(String error) {
        Result result = new Result.Error(error);
        userMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessFromLogOut() {
        this.userMutableLiveData.setValue(null);
    }
}
