package com.example.yummy.View.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummy.SearchByIngredient.Model.IngredientModel;
import com.example.yummy.R;
import com.example.yummy.SearchByIngredient.View.SearchByIngrdiantFragmentDirections;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<CountryAdapter.MyViewHolder>{
    ViewGroup CountryView;
    List<IngredientModel> ingrediance;

    public IngredientAdapter(List<IngredientModel>  ingrediance) {
        this.ingrediance =ingrediance ;
    }

    @NonNull
    @Override
    public CountryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CountryView =parent;

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item, parent, false);
        return new CountryAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.MyViewHolder holder, int position) {
        IngredientModel ingredientModel =ingrediance.get(position);
        holder.country.setText(ingredientModel.getStrIngredient());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            Navigation.findNavController(CountryView).navigate(SearchByIngrdiantFragmentDirections.actionSearchByIngrdiantFragmentToMealByIngrediantFragment(ingrediance.get(position).getStrIngredient()));

            }
        });
    }

    @Override
    public int getItemCount() {
        return ingrediance.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView country;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            country=itemView.findViewById(R.id.item_country);
        }
    }
}
