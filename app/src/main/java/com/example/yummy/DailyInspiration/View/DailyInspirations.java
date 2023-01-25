package com.example.yummy.DailyInspiration.View;


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

import com.example.yummy.DailyInspiration.Presenter.InterfaceDailyInspirations;
import com.example.yummy.DailyInspiration.Presenter.PresenterDailyInspirations;
import com.example.yummy.Model.MealsItem;
import com.example.yummy.R;
import com.example.yummy.View.Adapters.SliderAdapter;
import com.example.yummy.Model.SliderItemModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class DailyInspirations extends Fragment implements InterfaceDailyInspirations {

    private static final String TAG = "Home";

    //for the slider
    private ViewPager2 viewPager2;
    private List<SliderItemModel> sliderItemList;
    private Handler sliderHandler = new Handler();
    List<MealsItem> meals = new ArrayList<>();
    private PresenterDailyInspirations presenterDailyInspirations;



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

        viewPager2 = view.findViewById(R.id.viewPagerImageSlider);

        presenterDailyInspirations = new PresenterDailyInspirations(this);

        presenterDailyInspirations.getDailyInspirations();

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


    @Override
    public void responseOfDataOnSuccess(List<MealsItem> mealsList) {
        viewPager2.setAdapter(new SliderAdapter(mealsList, viewPager2));

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

    @Override
    public void responseOfDataOnFailure(Throwable error) {
        error.printStackTrace();
    }
}