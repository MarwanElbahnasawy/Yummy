package com.example.foodplanner.Controller.Fragments.MainFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleObserver;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.Model.MealsItem;
import com.example.foodplanner.R;
import com.example.foodplanner.View.MealDeatailIngrediantAdapter;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class MealDetailsFragment extends Fragment {
    TextView mealName, area, instructions;
    ImageView mealImage;
    YouTubePlayerView videoView;
    RecyclerView recyclerView;
    List<String> ingrediant = new ArrayList<>();
    MealDeatailIngrediantAdapter mealDeatailIngrediantAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MealsItem mealsItem = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealDetailsArgs();

        mealName = view.findViewById(R.id.tv_mealName);
        area = view.findViewById(R.id.tv_meal_area);
        instructions = view.findViewById(R.id.tv_Meal_instructions);
        mealImage = view.findViewById(R.id.mealImage);
        videoView = view.findViewById(R.id.video);
        recyclerView = view.findViewById(R.id.rv_ingrediant);

        getLifecycle().addObserver((LifecycleObserver) videoView);
        String[] split = mealsItem.getStrYoutube().split("=");

        videoView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = split[1];
                youTubePlayer.loadVideo(videoId, 0);
            }
        });

        mealName.setText(mealsItem.getStrMeal());
        area.setText(mealsItem.getStrArea());
        Glide.with(view.getContext()).load(mealsItem.getStrMealThumb()).into(mealImage);
        instructions.setText(mealsItem.getStrInstructions());

        getIngredient(mealsItem.getStrIngredient1());
        getIngredient(mealsItem.getStrIngredient2());
        getIngredient(mealsItem.getStrIngredient3());
        getIngredient(mealsItem.getStrIngredient4());
        getIngredient(mealsItem.getStrIngredient5());
        getIngredient(mealsItem.getStrIngredient6());
        getIngredient(mealsItem.getStrIngredient7());
        getIngredient(mealsItem.getStrIngredient8());
        getIngredient(mealsItem.getStrIngredient9());
        getIngredient(mealsItem.getStrIngredient10());
        getIngredient(mealsItem.getStrIngredient11());
        getIngredient(mealsItem.getStrIngredient12());
        getIngredient(mealsItem.getStrIngredient13());
        getIngredient(mealsItem.getStrIngredient14());
        getIngredient(mealsItem.getStrIngredient15());


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        mealDeatailIngrediantAdapter = new MealDeatailIngrediantAdapter(ingrediant);
        recyclerView.setAdapter(mealDeatailIngrediantAdapter);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    public MealDetailsFragment() {

    }

    private List<String> getIngredient(String ingredientName) {
        if (ingredientName != null && !ingredientName.isEmpty())
            ingrediant.add(ingredientName);
        return ingrediant;
    }

}