package com.example.welldrink.data.repository.user;

import androidx.lifecycle.MutableLiveData;

import com.example.welldrink.model.Result;
import com.example.welldrink.model.User;

public interface IUserRepository {

    MutableLiveData<Result> getUser(String email, String password, boolean isLogIn, String username);

    void signUp(String mail, String password, String username);

    void logIn(String mail, String password);

    User getLoggedUser();

    void logOut();

}
