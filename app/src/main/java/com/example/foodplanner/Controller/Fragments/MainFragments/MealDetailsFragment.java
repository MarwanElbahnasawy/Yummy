package com.example.foodplanner.Controller.Fragments.MainFragments;

import android.os.Bundle;
import android.util.Log;
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
    List<String> megure = new ArrayList<>();
    MealDeatailIngrediantAdapter mealDeatailIngrediantAdapter;
    String[] split;
    Boolean youtubeURLisExists = false;

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
        if(!mealsItem.getStrYoutube().isEmpty()){

            split = mealsItem.getStrYoutube().split("=");
            youtubeURLisExists = true;
            videoView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    if(youtubeURLisExists){
                        String videoId = split[1];
                        youTubePlayer.loadVideo(videoId, 0);
                    }
                }
            });
        }
        else{
            videoView.setVisibility(View.GONE);
        }

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
        getIngredient(mealsItem.getStrIngredient15());
        getMegure(mealsItem.getStrMeasure1());
        getMegure(mealsItem.getStrMeasure2());
        getMegure(mealsItem.getStrMeasure3());
        getMegure(mealsItem.getStrMeasure4());
        getMegure(mealsItem.getStrMeasure5());
        getMegure(mealsItem.getStrMeasure6());
        getMegure(mealsItem.getStrMeasure7());
        getMegure(mealsItem.getStrMeasure8());
        getMegure(mealsItem.getStrMeasure9());
        getMegure(mealsItem.getStrMeasure10());
        getMegure(mealsItem.getStrMeasure11());
        getMegure(mealsItem.getStrMeasure12());
        getMegure(mealsItem.getStrMeasure13());
        getMegure(mealsItem.getStrMeasure15());

        Log.i("esraa", "onViewCreated: "+ingrediant.size());

        Log.i("esraa", "onViewCreated: "+megure.size());



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        mealDeatailIngrediantAdapter = new MealDeatailIngrediantAdapter(ingrediant,megure);
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
    private List<String> getMegure(String ingredientName) {
        if (ingredientName != null && !ingredientName.isEmpty())
            megure.add(ingredientName);
        return megure;
    }

}