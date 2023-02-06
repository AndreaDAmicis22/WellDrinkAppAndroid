package com.example.welldrink.ui.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.transition.CircularPropagation;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.welldrink.R;
import com.example.welldrink.adapter.DetailRecyclerViewAdapter;
import com.example.welldrink.data.repository.drink.IDrinkRepository;
import com.example.welldrink.data.repository.user.IUserRepository;
import com.example.welldrink.model.Drink;
import com.example.welldrink.model.Ingredient;
import com.example.welldrink.model.Result;
import com.example.welldrink.ui.viewModel.DrinkViewModel;
import com.example.welldrink.ui.viewModel.DrinkViewModelFactory;
import com.example.welldrink.ui.viewModel.UserViewModel;
import com.example.welldrink.ui.viewModel.UserViewModelFactory;
import com.example.welldrink.util.ServiceLocator;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import jp.wasabeef.picasso.transformations.BlurTransformation;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = DetailsActivity.class.getSimpleName();
    private boolean onLike;

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
        ImageView image = findViewById(R.id.details_img);
        ImageView imageBg = findViewById(R.id.details_imgBg);
        TextView name = findViewById(R.id.details_txtTitle);
        TextView category = findViewById(R.id.details_info_txtCategory);
        TextView glass = findViewById(R.id.details_info_txtGlass);
        TextView alcol = findViewById(R.id.details_info_txtAlcol);
        TextView recipe = findViewById(R.id.details_prep_txtBody);
        RecyclerView detailsRecycleView = createRecyclerView();
        Bundle args = getIntent().getExtras();
        if (args != null) {
            drinkViewModel.getDrinkDetailsLiveData(args.getString("name")).observe(
                    this, result -> {
                        Log.d("API", "-Observer-");
                        if(result.isSuccess()){
                            CircularProgressIndicator loading = findViewById(R.id.details_progress);
                            loading.setVisibility(View.GONE);
                            ScrollView scrollView = findViewById(R.id.details_scroll);
                            scrollView.setVisibility(View.VISIBLE);
                            Log.d("API", "result.isSuccess");
                            drink = ((Result.Success<Drink>) result).getData();
                            Log.d("API", drink.toString());
                            RequestCreator imgReq = Picasso.get().load(drink.getImageUrl());
                            imgReq.into(image);
                            Log.d(TAG, String.valueOf(imageBg));
                            imgReq.transform(new BlurTransformation(this, 25, 1)).into(imageBg);
                            //Log.d("API", "terza volta non arriva qui?");
                            name.setText(drink.getName());
                            category.setText(drink.getCategory());
                            glass.setText(drink.getGlass());
                            alcol.setText(drink.getAlcolType());
                            recipe.setText(drink.getInstructions());
                            DetailRecyclerViewAdapter adapter = new DetailRecyclerViewAdapter(drink.getIngredientList());
                            //Log.d("API", "ARRIVO QUI");
                            detailsRecycleView.setAdapter(adapter);
                            //Log.d("API", "NON ARRIVO QUI");
                        }else{
                            Log.d("API", "result.isSuccess failed");
                        }
                    }
            );
        }
        Button btnLike = findViewById(R.id.details_btnLike);
        btnLike.setOnClickListener(view1 -> {
            onLike = likeOn(btnLike, onLike);
        });
        Button btnShare = findViewById(R.id.details_btnShare);
        btnShare.setOnClickListener(el -> {
            String ingredient = "Ingredient:"+"\n";
            for (Ingredient i : drink.getIngredientList()){
                ingredient += "_"+i.getName()+" "+i.getMeasure()+"\n";
            }
            String text = "Drink: "+drink.getName()+"\n"+"\n"+ingredient+"\n"
                    +"Glass: "+drink.getGlass()+"\n"+"\n"+"Recipe: "+drink.getInstructions()+"\n";
            Intent sharing = new Intent(Intent.ACTION_SEND);
            sharing.setType("text/plain");
            sharing.putExtra(Intent.EXTRA_SUBJECT, "Here is your drink!");
            sharing.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(sharing, "Share"));
        });
    }

    private RecyclerView createRecyclerView() {
        RecyclerView detailsRecyclerView = findViewById(R.id.details_recView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        detailsRecyclerView.setLayoutManager(linearLayoutManager);
        return detailsRecyclerView;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private Boolean likeOn(Button button, Boolean bool) {
        if (!bool)
            button.setBackground(getResources().getDrawable(R.drawable.ic_baseline_thumb_up_alt_24, this.getTheme()));
        else
            button.setBackground(getResources().getDrawable(R.drawable.ic_baseline_thumb_up_off_alt_24, this.getTheme()));
        return !bool;
    }
}