package com.example.foodplanner.Controller.Fragments.MainFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.Model.Root;
import com.example.foodplanner.Network.RetrofitClient;
import com.example.foodplanner.R;
import com.example.foodplanner.View.MealsByCountriesAdapter;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealByCountryFragment extends Fragment {

    MealsByCountriesAdapter mealsByCountriesAdapter;
    RecyclerView recyclerView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.rv_MealByCountry);
        String area = MealByCountryFragmentArgs.fromBundle(getArguments()).getArea();
        RetrofitClient.getInstance().getMyApi().getRoot(area).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Root>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull Root root) {
                        mealsByCountriesAdapter = new MealsByCountriesAdapter(root.getMeals());
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
                        recyclerView.setLayoutManager(linearLayoutManager);

                        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

                        recyclerView.setAdapter(mealsByCountriesAdapter);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_meal_by_country, container, false);
    }
}