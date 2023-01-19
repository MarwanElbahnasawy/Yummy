package com.example.foodplanner.Controller.Fragments.MainFragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.foodplanner.Model.MealsItem;
import com.example.foodplanner.Model.Root;
import com.example.foodplanner.Network.RetrofitClient;
import com.example.foodplanner.R;
import com.example.foodplanner.View.SliderAdapter;
import com.example.foodplanner.View.SliderItem;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Home extends Fragment {

    private static final String TAG = "Home";

    //for the slider
    private ViewPager2 viewPager2;
    private List<SliderItem> sliderItemList;
    private Handler sliderHandler = new Handler();
    List<MealsItem> meals = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "at home " + FirebaseAuth.getInstance().getCurrentUser());


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        getDailyInspirations();

        viewPager2 = view.findViewById(R.id.viewPagerImageSlider);


    }

    private void getDailyInspirations() {

        String[] countriesList = {"Indian", "Italian", "Chinese", "French", "British"};
        String randomCountry = countriesList[(new Random()).nextInt(countriesList.length)];

        Observable<Root> observableRandom1 = RetrofitClient.getInstance().getMyApi().getRootRandom();
        Observable<Root> observableRandom2 = RetrofitClient.getInstance().getMyApi().getRootRandom();
        Observable<Root> observableRandom3 = RetrofitClient.getInstance().getMyApi().getRootRandom();
        Observable<Root> observableRandom4 = RetrofitClient.getInstance().getMyApi().getRootRandom();
        Observable<Root> observableRandom5 = RetrofitClient.getInstance().getMyApi().getRootRandom();

        ArrayList<Observable<Root>> arrayListObservablesRandomMeal = new ArrayList<>(Arrays.asList(observableRandom1, observableRandom2, observableRandom3, observableRandom4, observableRandom5));

        Observable<Root> combinedObservable = Observable.merge(arrayListObservablesRandomMeal);

        combinedObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            meals.add(response.getMeals().get(0));
                        },

                        error -> {
                            error.printStackTrace();
                        },
                        () -> {

                            sliderItemList = new ArrayList<>();
                            for (int i = 0; i < 5; i++) {
                                sliderItemList.add(new SliderItem(meals.get(i).getStrMealThumb(), meals.get(i).getStrMeal()));
                            }

                            viewPager2.setAdapter(new SliderAdapter(sliderItemList, viewPager2));

                            //for slider to show 3 cards next to each other
                            viewPager2.setClipToPadding(false);
                            viewPager2.setClipChildren(false);
                            viewPager2.setOffscreenPageLimit(3);
                            viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_IF_CONTENT_SCROLLS);
                            CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
                            compositePageTransformer.addTransformer(new MarginPageTransformer(40));
                            compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
                                @Override
                                public void transformPage(@NonNull View page, float position) {
                                    float r = 1 - Math.abs(position);
                                    page.setScaleY(0.85f + r * 0.15f);

                                }
                            });
                            viewPager2.setPageTransformer(compositePageTransformer);

                            //for auto sliding part 1/2
                            viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                                @Override
                                public void onPageSelected(int position) {
                                    super.onPageSelected(position);
                                    sliderHandler.removeCallbacks(sliderRunnable);
                                    sliderHandler.postDelayed(sliderRunnable, 5000);
                                }
                            });

                        }
                );

    }

    //for auto sliding part 2/2
    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }

    };


    @Override
    public void onPause() {
        super.onPause();
        //to handle sliding at onPause
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        //to handle sliding at onResume
        sliderHandler.postDelayed(sliderRunnable, 5000);
    }


}