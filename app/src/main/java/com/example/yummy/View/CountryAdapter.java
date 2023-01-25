package com.example.yummy.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummy.Controller.Fragments.MainFragments.SearchBYCountryFragmentDirections;
import com.example.yummy.Model.AreaModel;
import com.example.yummy.R;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.MyViewHolder>{
    ViewGroup CountryView;
    List<AreaModel>  countries;

    public CountryAdapter(List<AreaModel>  countries) {
        this.countries = countries;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CountryView =parent;

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AreaModel areaModel=countries.get(position);
        holder.country.setText(areaModel.getStrArea());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Navigation.findNavController(CountryView).navigate(SearchBYCountryFragmentDirections.actionSearchBYCountryFragmentToMealByCountryFragment(countries.get(position).getStrArea()));

            }
        });
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView country;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            country=itemView.findViewById(R.id.item_country);
        }
    }
}
