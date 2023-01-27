package com.example.yummy.SearchByCategory.View;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummy.MainActivity.View.MainActivity;
import com.example.yummy.R;
import com.example.yummy.SearchByCategory.Models.EachCategoryModel;
import com.example.yummy.SearchByCategory.Presenter.InterfaceAllCategories;
import com.example.yummy.SearchByCategory.Presenter.PresenterAllCategories;
import com.example.yummy.Utility.NetworkChecker;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.stream.Collectors;

public class AllCategories extends Fragment implements InterfaceAllCategories {
    List<EachCategoryModel> categories;
    public static TextInputEditText textInputEditText;
    AllCategoriesAdapter allCategoriesAdapter;
    RecyclerView recyclerView;
    private PresenterAllCategories presenterAllCategories;
    private NetworkChecker networkChecker = NetworkChecker.getInstance();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        textInputEditText = view.findViewById(R.id.tinput_search_Categoreies);

        recyclerView = view.findViewById(R.id.rv_Categories);

        presenterAllCategories = new PresenterAllCategories(this);
        presenterAllCategories.getAllCategories();



        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                    allCategoriesAdapter = new AllCategoriesAdapter(categories.stream().filter(
                            AreaModel -> AreaModel.getStrCategory().toLowerCase().startsWith(s.toString().toLowerCase())).collect(Collectors.toList()));
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
                    linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(allCategoriesAdapter);
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


    @Override
    public void responseOfDataOnSuccess(List<EachCategoryModel> categoriesReceived) {
        categories = categoriesReceived;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        allCategoriesAdapter = new AllCategoriesAdapter(categories);
        recyclerView.setAdapter(allCategoriesAdapter);
    }
}