package com.example.yummy.Controller.Fragments.MainFragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummy.Model.AreaListModel;
import com.example.yummy.Model.AreaModel;
import com.example.yummy.Network.RetrofitClient;
import com.example.yummy.R;
import com.example.yummy.View.CountryAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchBYCountryFragment extends Fragment {
    List<AreaModel> countries;
    CountryAdapter countryAdapter;
    RecyclerView recyclerView;
    TextInputEditText textInputEditText;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_coun);
        textInputEditText=view.findViewById(R.id.tinput_search);


        Observable<AreaListModel> observableCountry = RetrofitClient.getInstance().getMyApi().getCountry();
        observableCountry.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                response -> {
                    countries = response.getMeals();

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
                    recyclerView.setLayoutManager(linearLayoutManager);

                    linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                    countryAdapter = new CountryAdapter(countries);
                    recyclerView.setAdapter(countryAdapter);
                    Log.i("eeeeee", "onViewCreated: " + countries.get(0).getStrArea());
                },
                error -> {
                    error.printStackTrace();
                }
        );


       textInputEditText .addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                countryAdapter = new CountryAdapter(countries.stream().filter(
                        AreaModel ->AreaModel.getStrArea() .startsWith(s.toString())).collect(Collectors.toList()));
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(countryAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_search_b_y_country, container, false);
    }
}