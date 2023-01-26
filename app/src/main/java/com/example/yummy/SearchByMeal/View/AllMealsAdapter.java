package com.example.yummy.SearchByMeal.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yummy.Model.MealsItem;
import com.example.yummy.Model.RootMeal;
import com.example.yummy.Network.RetrofitClient;
import com.example.yummy.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AllMealsAdapter extends RecyclerView.Adapter<AllMealsAdapter.MyViewHolder> {
    List<MealsItem> mealsItems = new ArrayList<>();
    ViewGroup viewGroup;

    public AllMealsAdapter(List<MealsItem> mealsItems) {
        this.mealsItems = mealsItems;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewGroup = parent;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_meal_items, parent, false);
        return new AllMealsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.mealName.setText(mealsItems.get(position).getStrMeal());
        Glide.with(viewGroup).load(mealsItems.get(position).getStrMealThumb())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.mealImage);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitClient.getInstance().getMyApi().getMealById(Integer.parseInt(mealsItems.get(position).getIdMeal()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<RootMeal>() {
                            @Override
                            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                                // loading ui
                            }

                            @Override
                            public void onNext(@io.reactivex.rxjava3.annotations.NonNull RootMeal rootMeal) {
                                Navigation.findNavController(viewGroup).navigate(AllMealsDirections.actionSearchByAllMealsFragmentToMealDeatailsFragment(rootMeal.getMeals().get(0)));
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });

            }
        });


    }

    @Override
    public int getItemCount() {
        return mealsItems.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mealName;
        CircleImageView mealImage;
        ImageButton btn_addToFavorites;
        AutoCompleteTextView autoCompleteTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName=itemView.findViewById(R.id.area_meal);
            mealImage=itemView.findViewById(R.id.areaMeal_image);
            btn_addToFavorites=itemView.findViewById(R.id.btn_add_favourite_search);
            autoCompleteTextView=itemView.findViewById(R.id.auto_complete_textview_search);

        }
    }
}
