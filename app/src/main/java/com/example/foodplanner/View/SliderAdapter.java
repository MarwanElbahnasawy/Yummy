package com.example.foodplanner.View;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.foodplanner.Controller.Fragments.MainFragments.HomeDirections;
import com.example.foodplanner.Model.MealsItem;
import com.example.foodplanner.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {


    ViewGroup viewGroupOfMeal;

    //private List<SliderItem> meals;
    private ViewPager2 viewPager2;
    List<MealsItem> meals = new ArrayList<>();

    public SliderAdapter(List<MealsItem> meals , ViewPager2 viewPager2) {
        this.meals = meals;
        this.viewPager2 = viewPager2;


    }



    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewGroupOfMeal = parent;
        return new SliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.slide_item_container,
                        parent,
                        false)

        );

    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //holder.imageView.setImageResource(sliderItemList.get(position).getImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(viewGroupOfMeal).navigate(HomeDirections.actionNavHomeToMealDeatailsFragment(meals.get(position)));

            }
        });
        MealsItem mealsItem = meals.get(position);
        Glide.with(viewGroupOfMeal.getContext()).load(mealsItem.getStrMealThumb()).into(holder.imageView);
        holder.tv_mealName.setText(mealsItem.getStrMeal());

        //Bookmark button on click:
        holder.img_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                //uploadData(userID , mealName);
            }
        });

        // to make scrolling infinite part 2/2
        if (position == meals.size() - 2) {
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView imageView;
        private TextView tv_mealName;
        private ImageView img_bookmark;

        SliderViewHolder(@NonNull View itemView) {

            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlide);
            tv_mealName = itemView.findViewById(R.id.tv_mealName);
            img_bookmark = itemView.findViewById(R.id.img_bookmark);
        }

    }

    // to make scrolling infinite part 1/2
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            meals.addAll(meals);
            notifyDataSetChanged();
        }
    };
}
