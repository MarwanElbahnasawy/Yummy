package com.example.foodplanner.Controller.Fragments.MainFragments;

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

import com.example.foodplanner.Model.CategoryListModel;
import com.example.foodplanner.Model.CategoryModel;
import com.example.foodplanner.Network.RetrofitClient;
import com.example.foodplanner.R;
import com.example.foodplanner.View.CategoryAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoryFragment extends Fragment {
    List<CategoryModel> categories;
    TextInputEditText textInputEditText;
    CategoryAdapter categoryAdapter;
    RecyclerView recyclerView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        textInputEditText = view.findViewById(R.id.tinput_search_Categoreies);

        recyclerView = view.findViewById(R.id.rv_Categories);
        Observable<CategoryListModel> observableCategory = RetrofitClient.getInstance().getMyApi().getCategory();

        observableCategory.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                response -> {
                    categories = response.getMeals();

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
                    recyclerView.setLayoutManager(linearLayoutManager);

                    linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                    categoryAdapter = new CategoryAdapter(categories);
                    recyclerView.setAdapter(categoryAdapter);
                    Log.i("eeeeee", "onViewCreated: " + categories.get(0).getStrCategory());
                },
                error -> {
                    error.printStackTrace();
                }
        );

        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                categoryAdapter = new CategoryAdapter(categories.stream().filter(
                        AreaModel -> AreaModel.getStrCategory().startsWith(s.toString())).collect(Collectors.toList()));
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(categoryAdapter);
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
        return inflater.inflate(R.layout.fragment_category, container, false);
    }
}