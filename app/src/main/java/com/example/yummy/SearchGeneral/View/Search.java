package com.example.yummy.SearchGeneral.View;

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

import com.example.yummy.R;


public class Search extends Fragment {

    CardView searchByCountry,searchByCategory,searchIngrediant;


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

     searchByCountry=view.findViewById(R.id.card_country);
     searchByCategory=view.findViewById(R.id.CardView_category);
     searchIngrediant=view.findViewById(R.id.cardView_ingrediant);
     searchByCountry.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Navigation.findNavController(view).navigate(SearchDirections.actionNavSearchToSearchBYCountryFragment());

         }
     });

     searchByCategory.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Navigation.findNavController(view).navigate(SearchDirections.actionNavSearchToCategoryFragment());

         }
     });
     searchIngrediant.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Navigation.findNavController(view).navigate(SearchDirections.actionNavSearchToSearchByIngrdiantFragment());

         }
     });
        super.onViewCreated(view, savedInstanceState);
    }
}