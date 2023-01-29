package com.example.yummy.DailyInspiration.View;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.example.yummy.DailyInspiration.Presenter.InterfaceDailyInspirations;
import com.example.yummy.DailyInspiration.Presenter.PresenterDailyInspirations;
import com.example.yummy.MainActivity.View.MainActivity;
import com.example.yummy.Model.MealsItem;
import com.example.yummy.R;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DailyInspirations extends Fragment implements InterfaceDailyInspirations {

    private static final String TAG = "DailyInspirations";


    private ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();
    private PresenterDailyInspirations presenterDailyInspirations;
    private RecyclerView recyclerViewPlanToday;
    private PlannedTodayAdapter plannedTodayAdapter;
    private List<MealsItem> allSavedMeals = new ArrayList<>();
    private List<MealsItem> mealsWeekPlannedToday = new ArrayList<>();
    private ProgressBar progressBar;
    private Boolean isPlannedTodayAdapterInstanceCreated = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();


        return inflater.inflate(R.layout.fragment_daily_inspirations, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager2 = view.findViewById(R.id.viewPagerImageSlider);
        progressBar = view.findViewById(R.id.loading);
        recyclerViewPlanToday = view.findViewById(R.id.recyclerViewPlannedTodayDailyInspirations);


        presenterDailyInspirations = new PresenterDailyInspirations(this, requireContext());

        presenterDailyInspirations.getDailyInspirations();

        if (!MainActivity.isLoginAsGuest) {
            presenterDailyInspirations.loadRoomFromFirestore();
        }

        if (!MainActivity.isLoginAsGuest) {
            presenterDailyInspirations.loadHeaderTitle();
        }


    }



    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }

    };


    @Override
    public void responseOfDataOnSuccess(List<MealsItem> mealsList) {
        viewPager2.setAdapter(new SliderAdapter(mealsList, viewPager2));

        hideProgress();


        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(50);
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


        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 5000);
            }
        });
    }

    @Override
    public void responseOfDataOnFailure(Throwable error) {
        error.printStackTrace();
    }

    @Override
    public void responseOfLoadingDataFromFirestoreToRoom() {
        allSavedMeals = presenterDailyInspirations.returnStoredMealsItems().blockingFirst();

        getMealsPlannedForToday();
    }

    private void getMealsPlannedForToday() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewPlanToday.setHasFixedSize(true);
        recyclerViewPlanToday.setLayoutManager(linearLayoutManager);
        List<MealsItem> returnStoredMealsItemsWithWeekDayNotNull = new ArrayList<>();

        for (MealsItem mealsItem : allSavedMeals) {
            if (!mealsItem.getWeekDay().equals("NULL")) {
                returnStoredMealsItemsWithWeekDayNotNull.add(mealsItem);
            }
        }


        for (MealsItem mealsItem : returnStoredMealsItemsWithWeekDayNotNull) {
            if (mealsItem.getWeekDay().toLowerCase().equals(LocalDate.now().getDayOfWeek().name().toLowerCase())) {
                mealsWeekPlannedToday.add(mealsItem);
            }
        }


        plannedTodayAdapter = PlannedTodayAdapter.getInstanceProvidingMeals(mealsWeekPlannedToday);
        recyclerViewPlanToday.setAdapter(plannedTodayAdapter);
        isPlannedTodayAdapterInstanceCreated = true;
    }

    @Override
    public void onPause() {
        super.onPause();

        sliderHandler.removeCallbacks(sliderRunnable);
        Log.i(TAG, "onPause: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        
        sliderHandler.postDelayed(sliderRunnable, 5000);
        mealsWeekPlannedToday.clear();

        Log.i(TAG, "onResume: ");
    }

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

}