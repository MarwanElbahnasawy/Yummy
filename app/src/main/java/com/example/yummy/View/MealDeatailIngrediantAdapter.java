package com.example.yummy.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yummy.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MealDeatailIngrediantAdapter extends RecyclerView.Adapter<MealDeatailIngrediantAdapter.MyViewHolder> {
    ViewGroup CountryView;
    List<String> mealsItems = new ArrayList<>();
    List<String> megure = new ArrayList<>();

    public MealDeatailIngrediantAdapter(List<String> mealsItems, List<String> megure) {
        this.mealsItems = mealsItems;
        this.megure=megure;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CountryView = parent;

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingrediant_item, parent, false);
        return new MealDeatailIngrediantAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.ingrediant.setText(mealsItems.get(position).toString());
        holder.megure.setText(megure.get(position).toString());
        Glide.with(CountryView).load(String.format("https://www.themealdb.com/images/ingredients/%s-Small.png", mealsItems.get(position)))
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {

        return mealsItems.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ingrediant,megure;
        CircleImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ingrediant = itemView.findViewById(R.id.ingrediant_text);
            imageView = itemView.findViewById(R.id.ingrediant_image);
            megure=itemView.findViewById(R.id.megure_text);
        }
    }
}
