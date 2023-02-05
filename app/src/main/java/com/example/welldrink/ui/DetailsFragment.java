package com.example.welldrink.ui;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import com.example.welldrink.model.Ingredient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailsFragment extends Fragment {

    private static final String TAG = DetailsFragment.class.getSimpleName();
    private boolean onLike;

    public DetailsFragment() {

    }

    public static DetailsFragment newInstance() {
        return new DetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        ImageView image = view.findViewById(R.id.details_img);
        TextView name = view.findViewById(R.id.details_txtTitle);
        TextView category = view.findViewById(R.id.details_info_txtCategory);
        TextView glass = view.findViewById(R.id.details_info_txtGlass);
        TextView alcol = view.findViewById(R.id.details_info_txtAlcol);
        Bundle args = getArguments();
        if (args != null) {
            name.setText(args.getString("name"));
            //Picasso.get().load(args.getString("img")).into(image);
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
        List<Ingredient> array = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            array.add(new Ingredient(0, "lemon", false, 1542, null, "10oz"));
        }
        RecyclerView detailsRecycleView = view.findViewById(R.id.details_recView);
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