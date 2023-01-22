package com.example.foodplanner.Controller.Fragments.MainFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.Model.MealsItem;
import com.example.foodplanner.Model.Repository;
import com.example.foodplanner.R;
import com.example.foodplanner.View.FavoriteMealsAdapter;
import com.example.foodplanner.View.WeekPlannerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WeekPlanner extends Fragment {

    private RecyclerView recyclerViewSaturday;
    private RecyclerView recyclerViewSunday;
    private RecyclerView recyclerViewMonday;
    private RecyclerView recyclerViewTuesday;
    private RecyclerView recyclerViewWednsday;
    private RecyclerView recyclerViewThursday;
    private RecyclerView recyclerViewFriday;
    WeekPlannerAdapter weekPlannerAdapterSaturday;
    WeekPlannerAdapter weekPlannerAdapterSunday;
    WeekPlannerAdapter weekPlannerAdapterMonday;
    WeekPlannerAdapter weekPlannerAdapterTuesday;
    WeekPlannerAdapter weekPlannerAdapterWednsday;
    WeekPlannerAdapter weekPlannerAdapterThursday;
    WeekPlannerAdapter weekPlannerAdapterFriday;
    List<MealsItem> mealsWeekPlanSaturday = new ArrayList<>();
    List<MealsItem> mealsWeekPlanSunday = new ArrayList<>();
    List<MealsItem> mealsWeekPlanMonday = new ArrayList<>();
    List<MealsItem> mealsWeekPlanTuesday = new ArrayList<>();
    List<MealsItem> mealsWeekPlanWednsday = new ArrayList<>();
    List<MealsItem> mealsWeekPlanThursday = new ArrayList<>();
    List<MealsItem> mealsWeekPlanFriday = new ArrayList<>();
    public static TextView tv_Saturday;
    public static TextView tv_Sunday;
    public static TextView tv_Monday;
    public static TextView tv_Tuesday;
    public static TextView tv_Wednsday;
    public static TextView tv_Thursday;
    public static TextView tv_Friday;

    private static final String TAG = "WeekPlanner";

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
        return inflater.inflate(R.layout.fragment_week_planner, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




        recyclerViewSaturday = view.findViewById(R.id.recyclerViewFavoriteMealsSaturday);
        recyclerViewSunday = view.findViewById(R.id.recyclerViewFavoriteMealsSunday);
        recyclerViewMonday = view.findViewById(R.id.recyclerViewFavoriteMealsMonday);
        recyclerViewTuesday = view.findViewById(R.id.recyclerViewFavoriteMealsTueday);
        recyclerViewWednsday = view.findViewById(R.id.recyclerViewFavoriteMealsWednsday);
        recyclerViewThursday = view.findViewById(R.id.recyclerViewFavoriteMealsThursday);
        recyclerViewFriday = view.findViewById(R.id.recyclerViewFavoriteMealsFriday);

        tv_Saturday = view.findViewById(R.id.tv_Saturday);
        tv_Sunday = view.findViewById(R.id.tv_Sunday);
        tv_Monday = view.findViewById(R.id.tv_Monday);
        tv_Tuesday = view.findViewById(R.id.tv_Tuesday);
        tv_Wednsday = view.findViewById(R.id.tv_Wednsday);
        tv_Thursday = view.findViewById(R.id.tv_Thursday);
        tv_Friday = view.findViewById(R.id.tv_Friday);

        List<RecyclerView> recyclerViewArrayList = new ArrayList<>();
        recyclerViewArrayList = Arrays.asList(recyclerViewSaturday,recyclerViewSunday,recyclerViewMonday,recyclerViewTuesday,recyclerViewWednsday,recyclerViewThursday,recyclerViewFriday);


        for (RecyclerView recyclerView: recyclerViewArrayList){
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(linearLayoutManager);
        }



        /* Weekplanner Firestore part 2/4: Getting data */
        /*
        getWeekPlanMealsUsingFirestore();

         */

        /* Weekplanner Room part 2/4: Getting data */

        rep=new Repository(requireContext());
        List<MealsItem> mealsItemsArrayList = rep.returnStoredMealsItems().blockingFirst();

        List<MealsItem> returnStoredMealsItemsWithWeekDayNotNull = new ArrayList<>();

        for(MealsItem mealsItem: mealsItemsArrayList){
            if(mealsItem.getWeekDay() != null){
                returnStoredMealsItemsWithWeekDayNotNull.add(mealsItem);
            }
        }

        List<MealsItem> mealsItemsSaturday = new ArrayList<>();
        List<MealsItem> mealsItemsSunday = new ArrayList<>();
        List<MealsItem> mealsItemsMonday = new ArrayList<>();
        List<MealsItem> mealsItemsTuesday = new ArrayList<>();
        List<MealsItem> mealsItemsWednsday = new ArrayList<>();
        List<MealsItem> mealsItemsThursday = new ArrayList<>();
        List<MealsItem> mealsItemsFriday = new ArrayList<>();

        for (MealsItem mealsItem : returnStoredMealsItemsWithWeekDayNotNull){
            switch (mealsItem.getWeekDay()){
                case "Saturday":
                    mealsItemsSaturday.add(mealsItem);
                    break;
                case "Sunday":
                    mealsItemsSunday.add(mealsItem);
                    break;
                case "Monday":
                    mealsItemsMonday.add(mealsItem);
                    break;
                case "Tuesday":
                    mealsItemsTuesday.add(mealsItem);
                    break;
                case "Wednsday":
                    mealsItemsWednsday.add(mealsItem);
                    break;
                case "Thursday":
                    mealsItemsThursday.add(mealsItem);
                    break;
                case "Friday":
                    mealsItemsFriday.add(mealsItem);
                    break;

            }
        }

        if(mealsItemsSaturday.size() != 0){
            tv_Saturday.setVisibility(View.VISIBLE);
            weekPlannerAdapterSaturday=new WeekPlannerAdapter(mealsItemsSaturday);
            recyclerViewSaturday.setAdapter(weekPlannerAdapterSaturday);
        }
        if(mealsItemsSunday.size() != 0){
            tv_Sunday.setVisibility(View.VISIBLE);
            weekPlannerAdapterSunday=new WeekPlannerAdapter(mealsItemsSunday);
            recyclerViewSunday.setAdapter(weekPlannerAdapterSunday);
        }
        if(mealsItemsMonday.size() != 0){
            tv_Monday.setVisibility(View.VISIBLE);
            weekPlannerAdapterMonday=new WeekPlannerAdapter(mealsItemsMonday);
            recyclerViewMonday.setAdapter(weekPlannerAdapterMonday);
        }
        if(mealsItemsTuesday.size() != 0){
            tv_Tuesday.setVisibility(View.VISIBLE);
            weekPlannerAdapterTuesday=new WeekPlannerAdapter(mealsItemsTuesday);
            recyclerViewTuesday.setAdapter(weekPlannerAdapterTuesday);
        }
        if(mealsItemsWednsday.size() != 0){
            tv_Wednsday.setVisibility(View.VISIBLE);
            weekPlannerAdapterWednsday=new WeekPlannerAdapter(mealsItemsWednsday);
            recyclerViewWednsday.setAdapter(weekPlannerAdapterWednsday);
        }
        if(mealsItemsThursday.size() != 0){
            tv_Thursday.setVisibility(View.VISIBLE);
            weekPlannerAdapterThursday=new WeekPlannerAdapter(mealsItemsThursday);
            recyclerViewThursday.setAdapter(weekPlannerAdapterThursday);
        }
        if(mealsItemsFriday.size() != 0){
            tv_Friday.setVisibility(View.VISIBLE);
            weekPlannerAdapterFriday=new WeekPlannerAdapter(mealsItemsFriday);
            recyclerViewFriday.setAdapter(weekPlannerAdapterFriday);
        }





    }

    private void getWeekPlanMealsUsingFirestore() {
        FirebaseFirestore.getInstance().collection("userWeekPlan")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               if (task.isSuccessful()) {
                                                   for (QueryDocumentSnapshot document : task.getResult()) {

                                                       if(document.get("userEmail").equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()) & document.get("weekDay").toString().equals("Saturday")){
                                                           mealsWeekPlanSaturday.add(new MealsItem(document.getId() ,document.get("strMeal").toString(),document.get("strArea").toString(), document.get("strMealThumb").toString(), document.get("strInstructions").toString(), document.get("strYoutube").toString(), document.get("weekDay").toString()));
                                                       }
                                                       else if(document.get("userEmail").equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()) & document.get("weekDay").toString().equals("Sunday")){
                                                           mealsWeekPlanSunday.add(new MealsItem(document.getId() ,document.get("strMeal").toString(),document.get("strArea").toString(), document.get("strMealThumb").toString(), document.get("strInstructions").toString(), document.get("strYoutube").toString(), document.get("weekDay").toString()));
                                                       }
                                                       else if(document.get("userEmail").equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()) & document.get("weekDay").toString().equals("Monday")){
                                                           mealsWeekPlanMonday.add(new MealsItem(document.getId() ,document.get("strMeal").toString(),document.get("strArea").toString(), document.get("strMealThumb").toString(), document.get("strInstructions").toString(), document.get("strYoutube").toString(), document.get("weekDay").toString()));
                                                       }
                                                       else if(document.get("userEmail").equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()) & document.get("weekDay").toString().equals("Tuesday")){
                                                           mealsWeekPlanTuesday.add(new MealsItem(document.getId() ,document.get("strMeal").toString(),document.get("strArea").toString(), document.get("strMealThumb").toString(), document.get("strInstructions").toString(), document.get("strYoutube").toString(), document.get("weekDay").toString()));
                                                       }
                                                       else if(document.get("userEmail").equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()) & document.get("weekDay").toString().equals("Wednsday")){
                                                           mealsWeekPlanWednsday.add(new MealsItem(document.getId() ,document.get("strMeal").toString(),document.get("strArea").toString(), document.get("strMealThumb").toString(), document.get("strInstructions").toString(), document.get("strYoutube").toString(), document.get("weekDay").toString()));
                                                       }
                                                       else if(document.get("userEmail").equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()) & document.get("weekDay").toString().equals("Thursday")){
                                                           mealsWeekPlanThursday.add(new MealsItem(document.getId() ,document.get("strMeal").toString(),document.get("strArea").toString(), document.get("strMealThumb").toString(), document.get("strInstructions").toString(), document.get("strYoutube").toString(), document.get("weekDay").toString()));
                                                       }
                                                       else if(document.get("userEmail").equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()) & document.get("weekDay").toString().equals("Friday")){
                                                           mealsWeekPlanFriday.add(new MealsItem(document.getId() ,document.get("strMeal").toString(),document.get("strArea").toString(), document.get("strMealThumb").toString(), document.get("strInstructions").toString(), document.get("strYoutube").toString(), document.get("weekDay").toString()));
                                                       }

                                                   }

                                                   if(mealsWeekPlanSaturday.size() != 0){
                                                       tv_Saturday.setVisibility(View.VISIBLE);
                                                       weekPlannerAdapterSaturday=new WeekPlannerAdapter(mealsWeekPlanSaturday);
                                                       recyclerViewSaturday.setAdapter(weekPlannerAdapterSaturday);
                                                   }
                                                   if(mealsWeekPlanSunday.size() != 0){
                                                       tv_Sunday.setVisibility(View.VISIBLE);
                                                       weekPlannerAdapterSunday=new WeekPlannerAdapter(mealsWeekPlanSunday);
                                                       recyclerViewSunday.setAdapter(weekPlannerAdapterSunday);
                                                   }
                                                   if(mealsWeekPlanMonday.size() != 0){
                                                       tv_Monday.setVisibility(View.VISIBLE);
                                                       weekPlannerAdapterMonday=new WeekPlannerAdapter(mealsWeekPlanMonday);
                                                       recyclerViewMonday.setAdapter(weekPlannerAdapterMonday);
                                                   }
                                                   if(mealsWeekPlanTuesday.size() != 0){
                                                       tv_Tuesday.setVisibility(View.VISIBLE);
                                                       weekPlannerAdapterTuesday=new WeekPlannerAdapter(mealsWeekPlanTuesday);
                                                       recyclerViewTuesday.setAdapter(weekPlannerAdapterTuesday);
                                                   }
                                                   if(mealsWeekPlanWednsday.size() != 0){
                                                       tv_Wednsday.setVisibility(View.VISIBLE);
                                                       weekPlannerAdapterWednsday=new WeekPlannerAdapter(mealsWeekPlanWednsday);
                                                       recyclerViewWednsday.setAdapter(weekPlannerAdapterWednsday);
                                                   }
                                                   if(mealsWeekPlanThursday.size() != 0){
                                                       tv_Thursday.setVisibility(View.VISIBLE);
                                                       weekPlannerAdapterThursday=new WeekPlannerAdapter(mealsWeekPlanThursday);
                                                       recyclerViewThursday.setAdapter(weekPlannerAdapterThursday);
                                                   }
                                                   if(mealsWeekPlanFriday.size() != 0){
                                                       tv_Friday.setVisibility(View.VISIBLE);
                                                       weekPlannerAdapterFriday=new WeekPlannerAdapter(mealsWeekPlanFriday);
                                                       recyclerViewFriday.setAdapter(weekPlannerAdapterFriday);
                                                   }



                                               } else {
                                                   Log.i(TAG, "Error getting documents.", task.getException());
                                               }
                                           }
                                       }
                );
    }


    @Override
    public void onPause() {
        super.onPause();
        //so when clicking an item then back, items dont get multiplied in the recycler views.
        mealsWeekPlanSaturday.clear();
        mealsWeekPlanSunday.clear();
        mealsWeekPlanMonday.clear();
        mealsWeekPlanTuesday.clear();
        mealsWeekPlanWednsday.clear();
        mealsWeekPlanThursday.clear();
        mealsWeekPlanFriday.clear();
    }
}