package com.example.welldrink.ui;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.BlendMode;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.welldrink.R;
import com.example.welldrink.adapter.DetailRecyclerViewAdapter;
import com.example.welldrink.model.Drink;
import com.example.welldrink.model.Ingredient;
import com.example.welldrink.model.Result;
import com.example.welldrink.ui.viewModel.DrinkViewModel;
import com.example.welldrink.ui.viewModel.UserViewModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.BlurTransformation;

public class DetailsFragment extends Fragment {

    private static final String TAG = DetailsFragment.class.getSimpleName();
    private boolean onLike;

    private Drink drink;

    private RecyclerView detailsRecycleView;
    private DrinkViewModel drinkViewModel;

    public DetailsFragment() {
        drink = new Drink();
    }

    public static DetailsFragment newInstance() {
        return new DetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drinkViewModel = new ViewModelProvider(requireActivity()).get(DrinkViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        ImageView image = view.findViewById(R.id.details_img);
        ImageView imageBg = view.findViewById(R.id.details_imgBg);
        TextView name = view.findViewById(R.id.details_txtTitle);
        TextView category = view.findViewById(R.id.details_info_txtCategory);
        TextView glass = view.findViewById(R.id.details_info_txtGlass);
        TextView alcol = view.findViewById(R.id.details_info_txtAlcol);
        TextView recipe = view.findViewById(R.id.details_prep_txtBody);
        Bundle args = getArguments();
        if (args != null) {
            drinkViewModel.getDrinkDetailsLiveData(args.getString("name")).observe(
                    requireActivity(), result -> {
                        if(result.isSuccess()){
                            Log.d("API", "result.isSuccess");
                            drink = ((Result.Success<Drink>) result).getData();
                            Log.d("API", drink.toString());
                            RequestCreator imgReq = Picasso.get().load(drink.getImageUrl());
                            imgReq.into(image);
                            imgReq.transform(new BlurTransformation(getContext(), 25, 2)).into(imageBg);
                            name.setText(drink.getName());
                            category.setText(drink.getCategory());
                            glass.setText(drink.getGlass());
                            alcol.setText(drink.getAlcolType());
                            recipe.setText(drink.getInstructions());
                            DetailRecyclerViewAdapter adapter = new DetailRecyclerViewAdapter(drink.getIngredientList());
                            Log.d("API", "ARRIVO QUI");
                            detailsRecycleView.setAdapter(adapter);
                            Log.d("API", "NON ARRIVO QUI");
                        }else{
                            Log.d("API", "result.isSuccess failed");
                        }
                    }
            );
        }
        Button btnLike = view.findViewById(R.id.details_btnLike);
        btnLike.setOnClickListener(view1 -> {
            onLike = likeOn(btnLike, onLike);
        });
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Ingredient> array = drink.getIngredientList();
//        for (int i = 0; i < 5; i++) {
//            array.add(new Ingredient(0, "lemon", false, 1542, null, "10oz"));
//        }
        detailsRecycleView = view.findViewById(R.id.details_recView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((requireContext()));
        detailsRecycleView.setLayoutManager(linearLayoutManager);
        DetailRecyclerViewAdapter adapter = new DetailRecyclerViewAdapter(array);
        detailsRecycleView.setAdapter(adapter);
    }

    private Boolean likeOn(Button button, Boolean bool) {
        if (!bool)
            button.setBackground(getResources().getDrawable(R.drawable.ic_baseline_thumb_up_alt_24, getContext().getTheme()));
        else
            button.setBackground(getResources().getDrawable(R.drawable.ic_baseline_thumb_up_off_alt_24, getContext().getTheme()));
        return !bool;
    }
}