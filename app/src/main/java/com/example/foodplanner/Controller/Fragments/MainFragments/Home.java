package com.example.foodplanner.Controller.Fragments.MainFragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends Fragment {

    private static final String TAG = "Home";

    //for the slider
    private ViewPager2 viewPager2;
    private List<SliderItem> sliderItemList;
    private Handler sliderHandler = new Handler();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



//        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
//            @Override
//            public void handleOnBackPressed() {
//                // Handle the back button even
//                Log.d("BACKBUTTON", "Back button clicks");
//            }
//        };
//
//        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);


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

//        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.nav_host_fragment_new);
//        NavController navController = navHostFragment.getNavController();
//        BottomNavigationView bottomNav = view.findViewById(R.id.activity_main_bottom_navigation_view);
//        NavigationUI.setupWithNavController(bottomNav, navController);

//
//        NavHostFragment.create(
//        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_new);
//        BottomNavigationView bottomNavigationView = view.findViewById(R.id.activity_main_bottom_navigation_view);
//        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        getDailyInspirations();

        viewPager2 = view.findViewById(R.id.viewPagerImageSlider);


    }

    private void getDailyInspirations() {

        String[] countriesList = {"Indian", "Italian", "Chinese", "French", "British"};
        String randomCountry = countriesList[(new Random()).nextInt(countriesList.length)];
        Call<Root> call = RetrofitClient.getInstance().getMyApi().getRoot(randomCountry);
        call.enqueue(new Callback<Root>() {

            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if (response.isSuccessful()) {
                    List<MealsItem> meals = response.body().getMeals();

                    sliderItemList = new ArrayList<>();
                    for(int i = 0 ; i<10 ; i++){
                        sliderItemList.add(new SliderItem(meals.get(i).getStrMealThumb(), meals.get(i).getStrMeal()));
                    }

                    viewPager2.setAdapter(new SliderAdapter(sliderItemList, viewPager2, requireContext()));

                    //for slider to show 3 cards next to each other
                    viewPager2.setClipToPadding(false);
                    viewPager2.setClipChildren(false);
                    viewPager2.setOffscreenPageLimit(4);
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
                            sliderHandler.postDelayed(sliderRunnable, 3000);
                        }
                    });
                    Log.i(TAG, "onResponse: " + meals.get(0).getStrMeal());
                }
            }
            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                t.printStackTrace();
            }
        });
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
        sliderHandler.postDelayed(sliderRunnable, 3000);
    }



}