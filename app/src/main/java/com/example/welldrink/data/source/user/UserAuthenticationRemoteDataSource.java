package com.example.welldrink.data.source.user;

import com.example.welldrink.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserAuthenticationRemoteDataSource extends BaseUserAuthenticationRemoteDataSource{

    private final FirebaseAuth firebaseAuth;

    public UserAuthenticationRemoteDataSource() { firebaseAuth = FirebaseAuth.getInstance();}

    @Override
    public void signUp(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    userResponseCallback.onSuccessFromAuthentication(new User(firebaseUser.getDisplayName(), email));
            }
        })
    }

}
