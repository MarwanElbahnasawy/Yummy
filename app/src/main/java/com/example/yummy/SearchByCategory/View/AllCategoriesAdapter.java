package com.example.yummy.SearchByCategory.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummy.SearchByCategory.Models.EachCategoryModel;
import com.example.yummy.R;

import java.util.List;

public class AllCategoriesAdapter extends RecyclerView.Adapter<AllCategoriesAdapter.MyViewHolder>{
    ViewGroup CountryView;
    List<EachCategoryModel> categories;

    public AllCategoriesAdapter(List<EachCategoryModel>  categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public AllCategoriesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CountryView =parent;

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item, parent, false);
        return new AllCategoriesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllCategoriesAdapter.MyViewHolder holder, int position) {
        EachCategoryModel eachCategoryModel =categories.get(position);
        holder.country.setText(eachCategoryModel.getStrCategory());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllCategories.textInputEditText.setText("");
                Navigation.findNavController(CountryView).navigate(AllCategoriesDirections.actionCategoryFragmentToMealByCategoryFragment(categories.get(position).getStrCategory()));

            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView country;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            country=itemView.findViewById(R.id.item_country);
        }

        public TextView getCountry() {
            return country;
        }
    }
}
