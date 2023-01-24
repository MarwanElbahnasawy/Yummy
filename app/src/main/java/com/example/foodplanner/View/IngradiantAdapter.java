package com.example.foodplanner.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.Controller.Fragments.MainFragments.CategoryFragmentDirections;
import com.example.foodplanner.Controller.Fragments.MainFragments.SearchByIngrdiantFragmentDirections;
import com.example.foodplanner.Model.CategoryModel;
import com.example.foodplanner.Model.GrediantModel;
import com.example.foodplanner.R;

import java.util.List;

public class IngradiantAdapter extends RecyclerView.Adapter<CountryAdapter.MyViewHolder>{
    ViewGroup CountryView;
    List<GrediantModel> ingrediance;

    public IngradiantAdapter(List<GrediantModel>  ingrediance) {
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
        GrediantModel grediantModel=ingrediance.get(position);
        holder.country.setText(grediantModel.getStrIngredient());
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
