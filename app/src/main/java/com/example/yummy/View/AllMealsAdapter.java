package com.example.yummy.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yummy.Controller.Fragments.MainFragments.SearchByAllMealsFragmentDirections;
import com.example.yummy.Model.MealsItem;
import com.example.yummy.Model.Root;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingrediant_item, parent, false);
        return new AllMealsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(mealsItems.get(position).getStrMeal());
        Glide.with(viewGroup).load(mealsItems.get(position).getStrMealThumb())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.circleImageView);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitClient.getInstance().getMyApi().getMealById(Integer.parseInt(mealsItems.get(position).getIdMeal()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Root>() {
                            @Override
                            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                                // loading ui
                            }

                            @Override
                            public void onNext(@io.reactivex.rxjava3.annotations.NonNull Root root) {
                                Navigation.findNavController(viewGroup).navigate(SearchByAllMealsFragmentDirections.actionSearchByAllMealsFragmentToMealDeatailsFragment(root.getMeals().get(0)));
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
        CircleImageView circleImageView;
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.ingrediant_image);
            textView = itemView.findViewById(R.id.ingrediant_text);

        }
    }
}
