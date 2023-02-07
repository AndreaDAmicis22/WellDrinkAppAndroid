package com.example.welldrink.data.source.user;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.welldrink.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UserAuthenticationRemoteDataSource extends BaseUserAuthenticationRemoteDataSource{

    private final FirebaseAuth firebaseAuth;

    public UserAuthenticationRemoteDataSource() { firebaseAuth = FirebaseAuth.getInstance();}

    @Override
    public void signUp(String email, String password, String username) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Log.d("AUTH", "Signup-taskSuccessful");
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                Log.d("AUTH", "Signup-firebaseUser");
                if(firebaseUser != null){
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(username).build();

                    firebaseUser.updateProfile(profileUpdates)
                            .addOnCompleteListener(task1 -> {
                                Log.d("AUTH", "USERNAME SET");
                            });
                    Log.d("AUTH", "Signup-firebaseUser != null");
                    userResponseCallback.onSuccessFromAuthentication(
                            new User(username, email, firebaseUser.getUid())
                    );
                }else{
                    Log.d("AUTH", "FirebaseUser == null");
                    Log.d("AUTH", task.getException().getMessage());
                    userResponseCallback.onFailureFromAuthentication(task.getException().getMessage());
                }
            }else{
                Log.d("AUTH", "Signup-taskFailed");
                Log.d("AUTH", task.getException().getMessage());
                userResponseCallback.onFailureFromAuthentication(task.getException().getMessage());
            }
        });
    }

    @Override
    public void signIn(String email, String password) {
        Log.d("AUTH", "dataSource SignIn with " + email + " " + password);
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser != null){
                    userResponseCallback.onSuccessFromAuthentication(new User(
                            firebaseUser.getDisplayName(), email, firebaseUser.getUid()
                    ));
                }else{
                    Log.d("AUTH", "FirebaseUser == null");
                    userResponseCallback.onFailureFromAuthentication(task.getException().getMessage());
                }
            }else{
                Log.d("AUTH", "login task failed");
                userResponseCallback.onFailureFromAuthentication(task.getException().getMessage());
            }
        });
    }

    @Override
    public User getLoggedUser() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser != null)
            return new User(firebaseUser.getDisplayName(), firebaseUser.getEmail(), firebaseUser.getUid());
        else
            return null;
    }

    @Override
    public void signOut() {
        FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    firebaseAuth.removeAuthStateListener(this);
                    Log.d("AUTH", "LOGOUT");
                }
            }
        };
        firebaseAuth.addAuthStateListener(authStateListener);
        firebaseAuth.signOut();
    }


}
