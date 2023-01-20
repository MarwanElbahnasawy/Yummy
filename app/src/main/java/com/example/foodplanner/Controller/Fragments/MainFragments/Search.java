package com.example.foodplanner.Controller.Fragments.MainFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplanner.Controller.Fragments.InitialFragments.OnBoardingDirections;
import com.example.foodplanner.R;


public class Search extends Fragment {

    CardView search;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

     search=view.findViewById(R.id.card_country);
     search.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Navigation.findNavController(view).navigate(SearchDirections.actionNavSearchToSearchBYCountryFragment());

         }
     });
        super.onViewCreated(view, savedInstanceState);
    }
}