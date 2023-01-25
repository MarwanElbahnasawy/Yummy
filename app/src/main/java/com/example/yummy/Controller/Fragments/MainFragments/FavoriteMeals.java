package com.example.yummy.Controller.Fragments.MainFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yummy.Model.MealsItem;
import com.example.yummy.Model.Repository;
import com.example.yummy.R;
import com.example.yummy.View.FavoriteMealsAdapter;

import java.util.ArrayList;
import java.util.List;


public class FavoriteMeals extends Fragment {

    private RecyclerView recyclerView;
    FavoriteMealsAdapter favoriteMealsAdapter;
    private static final String TAG = "SavedMeals";
    List<MealsItem> mealsFavorite = new ArrayList<>();

    Repository rep;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerViewFavoriteMeals);          //\\\\\\\\\\\
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        /* Favorites Firestore + Room part 2/4: Getting data */

        //--> no need to get from firestore when items are already saved in room.


        rep=new Repository(requireContext());

        List<MealsItem> returnStoredMealsItems = rep.returnStoredMealsItems().blockingFirst();
        List<MealsItem> returnStoredMealsItemsWithWeekDayNull = new ArrayList<>();

        for(MealsItem mealsItem: returnStoredMealsItems){
            if(mealsItem.getWeekDay().equals("NULL")){
                returnStoredMealsItemsWithWeekDayNull.add(mealsItem);
            }
        }

        favoriteMealsAdapter=new FavoriteMealsAdapter(returnStoredMealsItemsWithWeekDayNull);
        recyclerView.setAdapter(favoriteMealsAdapter);





    }



}