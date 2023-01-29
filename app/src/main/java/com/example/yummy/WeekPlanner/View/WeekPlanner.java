package com.example.yummy.WeekPlanner.View;

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
import android.widget.TextView;

import com.example.yummy.MainActivity.View.MainActivity;
import com.example.yummy.Model.MealsItem;
import com.example.yummy.R;
import com.example.yummy.WeekPlanner.Presenter.PresenterWeekPlanner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WeekPlanner extends Fragment {

    private RecyclerView recyclerViewSaturday, recyclerViewSunday, recyclerViewMonday, recyclerViewTuesday, recyclerViewWednesday, recyclerViewThursday, recyclerViewFriday;

    private WeekPlannerAdapter weekPlannerAdapterSaturday, weekPlannerAdapterSunday, weekPlannerAdapterMonday, weekPlannerAdapterTuesday, weekPlannerAdapterWednesday, weekPlannerAdapterThursday, weekPlannerAdapterFriday;

    private List<MealsItem> mealsWeekPlanSaturday = new ArrayList<>(), mealsWeekPlanSunday = new ArrayList<>(), mealsWeekPlanMonday = new ArrayList<>(), mealsWeekPlanTuesday = new ArrayList<>(), mealsWeekPlanWednesday = new ArrayList<>(), mealsWeekPlanThursday = new ArrayList<>(), mealsWeekPlanFriday = new ArrayList<>();

    public static TextView tv_Saturday, tv_Sunday, tv_Monday, tv_Tuesday, tv_Wednesday, tv_Thursday, tv_Friday;

    private static final String TAG = "WeekPlanner";

    private PresenterWeekPlanner presenterWeekPlanner;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_week_planner, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerViewSaturday = view.findViewById(R.id.recyclerViewFavoriteMealsSaturday);
        recyclerViewSunday = view.findViewById(R.id.recyclerViewFavoriteMealsSunday);
        recyclerViewMonday = view.findViewById(R.id.recyclerViewFavoriteMealsMonday);
        recyclerViewTuesday = view.findViewById(R.id.recyclerViewFavoriteMealsTueday);
        recyclerViewWednesday = view.findViewById(R.id.recyclerViewFavoriteMealsWednesday);
        recyclerViewThursday = view.findViewById(R.id.recyclerViewFavoriteMealsThursday);
        recyclerViewFriday = view.findViewById(R.id.recyclerViewFavoriteMealsFriday);

        tv_Saturday = view.findViewById(R.id.tv_Saturday);
        tv_Sunday = view.findViewById(R.id.tv_Sunday);
        tv_Monday = view.findViewById(R.id.tv_Monday);
        tv_Tuesday = view.findViewById(R.id.tv_Tuesday);
        tv_Wednesday = view.findViewById(R.id.tv_Wednesday);
        tv_Thursday = view.findViewById(R.id.tv_Thursday);
        tv_Friday = view.findViewById(R.id.tv_Friday);

        List<RecyclerView> recyclerViewArrayList = new ArrayList<>();
        recyclerViewArrayList = Arrays.asList(recyclerViewSaturday, recyclerViewSunday, recyclerViewMonday, recyclerViewTuesday, recyclerViewWednesday, recyclerViewThursday, recyclerViewFriday);


        for (RecyclerView recyclerView : recyclerViewArrayList) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(linearLayoutManager);
        }

        presenterWeekPlanner = new PresenterWeekPlanner(requireContext());
        List<MealsItem> mealsItemsArrayList = presenterWeekPlanner.returnStoredMealsItems().blockingFirst();

        List<MealsItem> returnStoredMealsItemsWithWeekDayNotNull = new ArrayList<>();

        for (MealsItem mealsItem : mealsItemsArrayList) {
            if (!mealsItem.getWeekDay().equals("NULL")) {
                returnStoredMealsItemsWithWeekDayNotNull.add(mealsItem);
            }
        }

        for (MealsItem mealsItem : returnStoredMealsItemsWithWeekDayNotNull) {
            switch (mealsItem.getWeekDay()) {
                case "Saturday":
                    mealsWeekPlanSaturday.add(mealsItem);
                    break;
                case "Sunday":
                    mealsWeekPlanSunday.add(mealsItem);
                    break;
                case "Monday":
                    mealsWeekPlanMonday.add(mealsItem);
                    break;
                case "Tuesday":
                    mealsWeekPlanTuesday.add(mealsItem);
                    break;
                case "Wednesday":
                    mealsWeekPlanWednesday.add(mealsItem);
                    break;
                case "Thursday":
                    mealsWeekPlanThursday.add(mealsItem);
                    break;
                case "Friday":
                    mealsWeekPlanFriday.add(mealsItem);
                    break;
            }
        }

        if (mealsWeekPlanSaturday.size() != 0) {
            tv_Saturday.setVisibility(View.VISIBLE);
            weekPlannerAdapterSaturday = new WeekPlannerAdapter(mealsWeekPlanSaturday);
            recyclerViewSaturday.setAdapter(weekPlannerAdapterSaturday);
        }
        if (mealsWeekPlanSunday.size() != 0) {
            tv_Sunday.setVisibility(View.VISIBLE);
            weekPlannerAdapterSunday = new WeekPlannerAdapter(mealsWeekPlanSunday);
            recyclerViewSunday.setAdapter(weekPlannerAdapterSunday);
        }
        if (mealsWeekPlanMonday.size() != 0) {
            tv_Monday.setVisibility(View.VISIBLE);
            weekPlannerAdapterMonday = new WeekPlannerAdapter(mealsWeekPlanMonday);
            recyclerViewMonday.setAdapter(weekPlannerAdapterMonday);
        }
        if (mealsWeekPlanTuesday.size() != 0) {
            tv_Tuesday.setVisibility(View.VISIBLE);
            weekPlannerAdapterTuesday = new WeekPlannerAdapter(mealsWeekPlanTuesday);
            recyclerViewTuesday.setAdapter(weekPlannerAdapterTuesday);
        }
        if (mealsWeekPlanWednesday.size() != 0) {
            tv_Wednesday.setVisibility(View.VISIBLE);
            weekPlannerAdapterWednesday = new WeekPlannerAdapter(mealsWeekPlanWednesday);
            recyclerViewWednesday.setAdapter(weekPlannerAdapterWednesday);
        }
        if (mealsWeekPlanThursday.size() != 0) {
            tv_Thursday.setVisibility(View.VISIBLE);
            weekPlannerAdapterThursday = new WeekPlannerAdapter(mealsWeekPlanThursday);
            recyclerViewThursday.setAdapter(weekPlannerAdapterThursday);
        }
        if (mealsWeekPlanFriday.size() != 0) {
            tv_Friday.setVisibility(View.VISIBLE);
            weekPlannerAdapterFriday = new WeekPlannerAdapter(mealsWeekPlanFriday);
            recyclerViewFriday.setAdapter(weekPlannerAdapterFriday);
        }
    }


    @Override
    public void onPause() {
        super.onPause();

        mealsWeekPlanSaturday.clear();
        mealsWeekPlanSunday.clear();
        mealsWeekPlanMonday.clear();
        mealsWeekPlanTuesday.clear();
        mealsWeekPlanWednesday.clear();
        mealsWeekPlanThursday.clear();
        mealsWeekPlanFriday.clear();
    }
}