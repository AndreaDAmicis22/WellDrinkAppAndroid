package com.example.welldrink.data.source.drinks;

import static com.example.welldrink.util.Constants.DB_FAVORITEDRINK;
import static com.example.welldrink.util.Constants.DB_FAVORITEINGREDIENTS;
import static com.example.welldrink.util.Constants.DB_REALTIME;
import static com.example.welldrink.util.Constants.DB_USER;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FavoriteDrinkDataSource extends BaseFavoriteDrinkDataSource {

    private final DatabaseReference databaseReference;
    private final String userToken;

    public FavoriteDrinkDataSource(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance(DB_REALTIME);
        databaseReference = firebaseDatabase.getReference().getRef();
        userToken = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }


    @Override
    public void setDrinkFavorite(String name) {
        databaseReference.child(DB_USER).child(userToken)
                .child(DB_FAVORITEDRINK)
                .child(String.valueOf(name.hashCode()))
                .setValue(name).addOnSuccessListener(a -> {
                    Log.d("FIRE", "Messo a favorite");
                    drinkResponseCallback.onSuccessFromAddingFavorite(name);
                }).addOnFailureListener(err -> {
                    Log.d("FIRE", "ERRORE in favorite");
                });
    }

    @Override
    public void setDrinkUnFavorite(String name) {
        databaseReference.child(DB_USER).child(userToken)
                .child(DB_FAVORITEDRINK).child(String.valueOf(name.hashCode())).removeValue()
                .addOnSuccessListener(a -> {
                    Log.d("FIRE", "Tolto da favorites");
                    drinkResponseCallback.onSuccessFromRemovingFavorite(name);
                }).addOnFailureListener(err -> {
                    Log.d("FIRE", "ERRORE in remove from favorite");
                });
    }

    @Override
    public void fetchDrinkFavorite() {
        databaseReference.child(DB_USER).child(userToken).child(DB_FAVORITEDRINK).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Log.d("FIRE", "task.isSuccessful()");
//                Log.d("FIRE", task.getResult().getValue().toString());
                if(task.getResult().getValue() != null){
                    Map<String, String> favorites = (HashMap<String, String>) task.getResult().getValue();
                    drinkResponseCallback.onSuccessFromFetchFavorite(new ArrayList<>(favorites.values()));
                }else{
                    Log.d("FIRE", "task.getResult().getValue() == null");
                }
            }else{
                Log.d("FIRE", "ERROR task.isSuccessful()");
            }
        });
    }

    @Override
    public void setIngredientFavorite(String name) {
        databaseReference.child(DB_USER).child(userToken)
                .child(DB_FAVORITEINGREDIENTS).child(String.valueOf(name.hashCode()))
                .setValue(name).addOnSuccessListener(a -> {
                    drinkResponseCallback.onSuccessFromAddingFavoriteIngredient(name);
                }).addOnFailureListener(err -> {
                    Log.d("FIRE", "ERROR setIngredientFavorite");
                });
    }

    @Override
    public void setIngredientUnfavorite(String name) {
        databaseReference.child(DB_USER).child(userToken)
                .child(DB_FAVORITEINGREDIENTS).child(String.valueOf(name.hashCode())).removeValue()
                .addOnSuccessListener(a -> {
                    Log.d("FIRE", "Tolto da favorites");
                    drinkResponseCallback.onSuccessFromRemovingFavoriteIngredient(name);
                }).addOnFailureListener(err -> {
                    Log.d("FIRE", "ERRORE in remove from favorite");
                });
    }

    @Override
    public void fetchIngredientFavorite() {
        databaseReference.child(DB_USER).child(userToken)
                .child(DB_FAVORITEINGREDIENTS).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        if(task.getResult().getValue() != null){
                            Map<String, String> favorites = (HashMap<String, String>) task.getResult().getValue();
                            Log.d("FIRE", favorites.values().toString());
                            drinkResponseCallback.onSuccessFromFetchFavoriteIngredients(new ArrayList<>(favorites.values()));
                        }else{
                            Log.d("FIRE", "task.getResult().getValue() == null");
                        }
                    }else{
                        Log.d("FIRE", "fetchIngredientFavorite -> error in fetch");
                    }
                });
    }
}
