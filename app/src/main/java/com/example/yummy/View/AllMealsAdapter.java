package com.example.yummy.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yummy.Model.MealsItem;
import com.example.yummy.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllMealsAdapter extends RecyclerView.Adapter<AllMealsAdapter.MyViewHolder> {
    List <MealsItem>mealsItems=new ArrayList<>();
    ViewGroup viewGroup;

    public  AllMealsAdapter(List<MealsItem>mealsItems){
       this. mealsItems=mealsItems;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            viewGroup=parent;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingrediant_item, parent, false);
        return new AllMealsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(mealsItems.get(position).getStrMeal());
        Glide.with(viewGroup).load( mealsItems.get(position).getStrMealThumb())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.circleImageView);


    }

    @Override
    public int getItemCount() {
        return mealsItems.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CircleImageView circleImageView;
        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView=itemView.findViewById(R.id.ingrediant_image);
            textView=itemView.findViewById(R.id.ingrediant_text);

        }
    }
}
