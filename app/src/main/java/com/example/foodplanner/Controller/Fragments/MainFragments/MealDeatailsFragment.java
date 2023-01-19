package com.example.foodplanner.Controller.Fragments.MainFragments;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.foodplanner.Model.MealsItem;
import com.example.foodplanner.R;

public class MealDeatailsFragment extends Fragment {
    TextView mealName,area,description;
    ImageView mealImage;
    VideoView videoView;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MealsItem mealsItem=MealDeatailsFragmentArgs.fromBundle(getArguments()).getMealDetailsArges();
        mealName=view.findViewById(R.id.tv_mealName);
        area=view.findViewById(R.id.tv_meal_area);
        description=view.findViewById(R.id.tv_Meal_description);
        mealImage=view.findViewById(R.id.mealImage);
        videoView=view.findViewById(R.id.video);

        //Creating MediaController
        MediaController mediaController= new MediaController(getContext());
        mediaController.setAnchorView(videoView);

        //specify the location of media file
        Uri uri=Uri.parse(Environment.getExternalStorageDirectory().getPath()+mealsItem.getStrYoutube());

        //Setting MediaController and URI, then starting the videoView
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();


        mealName.setText(mealsItem.getStrMeal());
        area.setText(mealsItem.getStrArea());
        Glide.with(view.getContext()).load(mealsItem.getStrMealThumb()).into(mealImage);
        description.setText(mealsItem.getStrInstructions());




    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    public MealDeatailsFragment(){

    }
}