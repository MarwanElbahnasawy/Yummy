package com.example.foodplanner.View;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.Controller.Fragments.MainFragments.FavoriteMealsDirections;
import com.example.foodplanner.Model.MealsItem;
import com.example.foodplanner.Model.Repository;
import com.example.foodplanner.Model.RootSingleMeal;
import com.example.foodplanner.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class FavoriteMealsAdapter extends RecyclerView.Adapter<FavoriteMealsAdapter.ViewHolder> {

    private static final String TAG = "FavoriteMealsAdapter";
    List<MealsItem> mealsFavorite = new ArrayList<>();
    Observable<RootSingleMeal> observableMealSelectedFromFavorites;
    Repository rep;
    private ViewGroup viewGroup;


    public FavoriteMealsAdapter(List<MealsItem> mealsFavorite) {
        this.mealsFavorite = mealsFavorite;
    }

    @NonNull
    @Override
    public FavoriteMealsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        this.viewGroup = parent;

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.row_favorite_meals, parent, false);          //\\\\\\\\\\
        ViewHolder viewHolder = new ViewHolder(itemView);
        Log.i(TAG, "onCreateViewHolder: ");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMealsAdapter.ViewHolder holder, int position) {

        /* Favorites Firestore part 3/4: Loading in recycler view and Removing */
/*
        MealsItem mealsItem = mealsFavorite.get(position);
        holder.fav_tv_mealName.setText(mealsItem.getStrMeal());
        holder.fav_tv_mealArea.setText(mealsItem.getStrArea());

        Glide.with(viewGroup.getContext()).load(mealsItem.getStrMealThumb()).into(holder.fav_img_mealImg);
        holder.btn_removeFromFavorites.setOnClickListener(new View.OnClickListener() {        //\\\\\\\
            @Override
            public void onClick(View view) {
                FirebaseFirestore.getInstance().collection("userFavorites").document(mealsItem.documentID)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.i(TAG, "DocumentSnapshot successfully deleted!");
                                //(FavoriteMealsAdapter.this).notifyDataSetChanged();
                                mealsFavorite.remove(position);
                                notifyDataSetChanged();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i(TAG, "Error deleting document", e);
                            }
                        });                                            //\\\\\\\
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                Navigation.findNavController(viewGroup).navigate(FavoriteMealsDirections.actionNavFavoriteMealsToMealDeatailsFragment(mealsFavorite.get(position)));
                mealsFavorite.clear(); //cause clicked back multiplied whats shown in the view.

            }
        });

 */



        /* Favorites Room part 3/4:  Loading in recycler view and Removing */


        MealsItem mealsItem = mealsFavorite.get(position);
        holder.fav_tv_mealName.setText(mealsItem.getStrMeal());
        holder.fav_tv_mealArea.setText(mealsItem.getStrArea());

        Glide.with(viewGroup.getContext()).load(mealsItem.getStrMealThumb()).into(holder.fav_img_mealImg);

        holder.btn_removeFromFavorites.setOnClickListener(new View.OnClickListener() {        //\\\\\\\
            @Override
            public void onClick(View view) {

                rep = new Repository(viewGroup.getContext());
                rep.delete(mealsItem);
                mealsFavorite.remove(position);
                notifyDataSetChanged();

            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Navigation.findNavController(viewGroup).navigate(FavoriteMealsDirections.actionNavFavoriteMealsToMealDeatailsFragment(mealsFavorite.get(position)));
                mealsFavorite.clear(); //cause clicked back multiplied whats shown in the view.

            }
        });

    }

    @Override
    public int getItemCount() {

        /* Favorites Firestore part 4/4: Getting item count */
        /*
         return mealsFavorite.size();

         */


        /* Favorites Room part 4/4: Getting item count */
        return mealsFavorite.size();


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView fav_tv_mealName;
        public TextView fav_tv_mealArea;
        public ImageView fav_img_mealImg;
        public Button btn_removeFromFavorites;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fav_tv_mealName = itemView.findViewById(R.id.fav_tv_mealName);
            fav_tv_mealArea = itemView.findViewById(R.id.fav_tv_mealArea);
            fav_img_mealImg = itemView.findViewById(R.id.fav_img_mealImg);
            btn_removeFromFavorites = itemView.findViewById(R.id.btn_removeFav);

        }
    }
}