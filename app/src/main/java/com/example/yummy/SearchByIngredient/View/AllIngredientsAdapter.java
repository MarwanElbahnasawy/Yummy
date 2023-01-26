package com.example.yummy.SearchByIngredient.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummy.SearchByIngredient.Model.EachIngredientModel;
import com.example.yummy.R;

import java.util.List;

public class AllIngredientsAdapter extends RecyclerView.Adapter<AllIngredientsAdapter.MyViewHolder>{
    ViewGroup CountryView;
    List<EachIngredientModel> ingrediance;

    public AllIngredientsAdapter(List<EachIngredientModel>  ingrediance) {
        this.ingrediance =ingrediance ;
    }

    @NonNull
    @Override
    public AllIngredientsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CountryView =parent;

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item, parent, false);
        return new AllIngredientsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllIngredientsAdapter.MyViewHolder holder, int position) {
        EachIngredientModel eachIngredientModel =ingrediance.get(position);
        holder.country.setText(eachIngredientModel.getStrIngredient());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            AllIngredients.textInputEditText.setText("");
            Navigation.findNavController(CountryView).navigate(AllIngredientsDirections.actionSearchByIngrdiantFragmentToMealByIngrediantFragment(ingrediance.get(position).getStrIngredient()));

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
