package com.example.welldrink.ui.Activity;

import static com.example.welldrink.util.ErrorSnackbars.handleDrinkError;
import static com.example.welldrink.util.ErrorSnackbars.handlePicassoError;
import static com.example.welldrink.util.LikeHandler.getFilled;
import static com.example.welldrink.util.LikeHandler.getUnfilled;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.welldrink.R;
import com.example.welldrink.adapter.DetailsRecyclerViewAdapter;
import com.example.welldrink.data.repository.drink.IDrinkRepository;
import com.example.welldrink.model.Drink;
import com.example.welldrink.model.Ingredient;
import com.example.welldrink.model.Result;
import com.example.welldrink.ui.viewModel.DrinkViewModel;
import com.example.welldrink.ui.viewModel.DrinkViewModelFactory;
import com.example.welldrink.util.ServiceLocator;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import jp.wasabeef.picasso.transformations.BlurTransformation;

public class DetailsActivity extends AppCompatActivity {

    private Drink drink;

    private DrinkViewModel drinkViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        drink = new Drink();
        IDrinkRepository drinkRepository = ServiceLocator.getInstance().getDrinkRepository();
        drinkViewModel = new ViewModelProvider(
                this,
                new DrinkViewModelFactory(drinkRepository)).get(DrinkViewModel.class);
        RecyclerView detailsRecycleView = createRecyclerView();
        Bundle args = getIntent().getExtras();
        Button btnLike = findViewById(R.id.details_btnLike);
        Button btnShare = findViewById(R.id.details_btnShare);
        if (args != null) {
            drinkViewModel.getDrinkDetailsLiveData(args.getString("name")).observe(
                    this, result -> {
                        Log.d("API", "-Observer-");
                        if (result.isSuccess()) {
                            Log.d("API", "result.isSuccess");
                            drink = ((Result.Success<Drink>) result).getData();
                            drink.setFavorite(args.getBoolean("fav"));
                            Log.d("API", drink.toString());
                            handleImages();
                            changeTextViews();
                            if (drink.isFavorite())
                                btnLike.setBackground(getFilled(getResources(), getTheme()));
                            else
                                btnLike.setBackground(getUnfilled(getResources(), getTheme()));
                            DetailsRecyclerViewAdapter adapter = new DetailsRecyclerViewAdapter(drink.getIngredientList());
                            detailsRecycleView.setAdapter(adapter);
                        } else {
                            Log.d("API", "result.isSuccess failed");
                            handleDrinkError(findViewById(android.R.id.content));
                        }
                    }
            );
        }
        btnLike.setOnClickListener(el -> {
            if (drink.isFavorite()) {
                btnLike.setBackground(getUnfilled(getResources(), getTheme()));
                drinkViewModel.setDrinkUnfavorite(drink.getName());
            } else {
                btnLike.setBackground(getFilled(getResources(), getTheme()));
                drinkViewModel.setDrinkFavorite(drink.getName());
            }
            drink.setFavorite(!drink.isFavorite());
        });
        btnShare.setOnClickListener(el -> startActivity(Intent.createChooser(handleShare(), "Share")));
    }

    private void handleImages() {
        RequestCreator imgReq = Picasso.get().load(drink.getImageUrl());
        imgReq.into((ImageView) findViewById(R.id.details_img));
        imgReq.transform(new BlurTransformation(this, 25, 1)).
                into((ImageView) findViewById(R.id.details_imgBg), new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        removeLoadingScreen();
                    }

                    @Override
                    public void onError(Exception e) {
                        handlePicassoError(findViewById(android.R.id.content));
                    }
                });
    }

    private void removeLoadingScreen() {
        CircularProgressIndicator loading = findViewById(R.id.details_progress);
        loading.setVisibility(View.GONE);
        NestedScrollView scrollView = findViewById(R.id.details_scroll);
        scrollView.setVisibility(View.VISIBLE);
    }

    private void changeTextViews() {
        ((TextView) findViewById(R.id.details_txtTitle)).setText(drink.getName());
        ((TextView) findViewById(R.id.details_info_txtCategory)).setText(drink.getCategory());
        ((TextView) findViewById(R.id.details_info_txtGlass)).setText(drink.getGlass());
        ((TextView) findViewById(R.id.details_info_txtAlcol)).setText(drink.getAlcolType());
        ((TextView) findViewById(R.id.details_prep_txtBody)).setText(drink.getInstructions());
    }

    private Intent handleShare() {
        StringBuilder ingredient = new StringBuilder("Ingredient:" + "\n");
        for (Ingredient i : drink.getIngredientList()) {
            ingredient.append("_").append(i.getName()).append(" ").append(i.getMeasure()).append("\n");
        }
        String text = "Drink: " + drink.getName() + "\n" + "\n" + ingredient + "\n"
                + "Glass: " + drink.getGlass() + "\n" + "\n" + "Recipe: " + drink.getInstructions() + "\n";
        Intent sharing = new Intent(Intent.ACTION_SEND);
        sharing.setType("text/plain");
        sharing.putExtra(Intent.EXTRA_SUBJECT, "Here is your drink!");
        sharing.putExtra(Intent.EXTRA_TEXT, text);
        return sharing;
    }

    private RecyclerView createRecyclerView() {
        RecyclerView detailsRecyclerView = findViewById(R.id.details_recView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        detailsRecyclerView.setLayoutManager(linearLayoutManager);
        return detailsRecyclerView;
    }
}