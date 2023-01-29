package com.example.yummy.SearchByCategory.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummy.MainActivity.View.MainActivity;
import com.example.yummy.SearchByCategory.Models.EachCategoryModel;
import com.example.yummy.R;
import com.example.yummy.Utility.NetworkChecker;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllCategoriesAdapter extends RecyclerView.Adapter<AllCategoriesAdapter.MyViewHolder> {
    ViewGroup CountryView;
    List<EachCategoryModel> categories;
    private NetworkChecker networkChecker = NetworkChecker.getInstance();

    public AllCategoriesAdapter(List<EachCategoryModel> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public AllCategoriesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CountryView = parent;

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item, parent, false);
        return new AllCategoriesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllCategoriesAdapter.MyViewHolder holder, int position) {
        EachCategoryModel eachCategoryModel = categories.get(position);
        holder.country.setText(eachCategoryModel.getStrCategory());
        String category = categories.get(position).getStrCategory();

        if (category.equals("Beef")) {
            holder.circleImageView.setImageResource(R.drawable.beef);
        } else if (category.equals("Breakfast")) {
            holder.circleImageView.setImageResource(R.drawable.breakfast);
        } else if (category.equals("Chicken")) {
            holder.circleImageView.setImageResource(R.drawable.chicken);
        } else if (category.equals("Dessert")) {
            holder.circleImageView.setImageResource(R.drawable.dessert);
        } else if (category.equals("Goat")) {
            holder.circleImageView.setImageResource(R.drawable.goat);
        } else if (category.equals("Lamb")) {
            holder.circleImageView.setImageResource(R.drawable.lamb);
        } else if (category.equals("Miscellaneous")) {
            holder.circleImageView.setImageResource(R.drawable.miscellllan);
        } else if (category.equals("Pasta")) {
            holder.circleImageView.setImageResource(R.drawable.pasta);
        } else if (category.equals("Pork")) {
            holder.circleImageView.setImageResource(R.drawable.pork);
        } else if (category.equals("Seafood")) {
            holder.circleImageView.setImageResource(R.drawable.seafood);
        } else if (category.equals("Side")) {
            holder.circleImageView.setImageResource(R.drawable.side);
        } else if (category.equals("Starter")) {
            holder.circleImageView.setImageResource(R.drawable.starter);
        } else if (category.equals("Vegan")) {
            holder.circleImageView.setImageResource(R.drawable.vegan);
        } else if (category.equals("Vegetarian")) {
            holder.circleImageView.setImageResource(R.drawable.vegiterian);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!networkChecker.checkIfInternetIsConnected()) {
                    MainActivity.mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.mainActivity, "Turn internet on to view meals related to this category.", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else if (networkChecker.checkIfInternetIsConnected()) {
                    AllCategories.textInputEditText.setText("");
                    Navigation.findNavController(CountryView).navigate(AllCategoriesDirections.actionCategoryFragmentToMealByCategoryFragment(categories.get(position).getStrCategory()));

                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView country;
        CircleImageView circleImageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            country = itemView.findViewById(R.id.item_country);
            circleImageView = itemView.findViewById(R.id.country_image);
        }

        public TextView getCountry() {
            return country;
        }
    }
}
