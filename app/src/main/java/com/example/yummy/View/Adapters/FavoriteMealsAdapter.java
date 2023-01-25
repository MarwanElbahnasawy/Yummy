package com.example.yummy.View.Adapters;

import android.app.ProgressDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yummy.Favorites.FavoriteMealsDirections;
import com.example.yummy.MainActivity;
import com.example.yummy.Model.MealsItem;
import com.example.yummy.Repository.Model.Repository;
import com.example.yummy.Model.RootSingleMeal;
import com.example.yummy.R;
import com.example.yummy.Utility.NetworkChecker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class FavoriteMealsAdapter extends RecyclerView.Adapter<FavoriteMealsAdapter.ViewHolder> {

    private ViewGroup viewGroup;
    List<MealsItem> mealsFavorite = new ArrayList<>();
    private static final String TAG = "FavoriteMealsAdapter";
    Observable<RootSingleMeal> observableMealSelectedFromFavorites;

    Repository rep;

    private ProgressDialog progressDialog;



    public FavoriteMealsAdapter(List<MealsItem> mealsFavorite) {
        this.mealsFavorite = mealsFavorite;
    }

    @NonNull
    @Override
    public FavoriteMealsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        this.viewGroup = parent;

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.row_favorite_meals, parent , false);          //\\\\\\\\\\
        ViewHolder viewHolder = new ViewHolder(itemView);
        Log.i(TAG, "onCreateViewHolder: ");

        progressDialog = new ProgressDialog(viewGroup.getContext());
        progressDialog.setTitle("Removing meal from favorites");
        progressDialog.setMessage("Please wait while removing the selected item from your favorites.");
        progressDialog.setCanceledOnTouchOutside(true);

        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMealsAdapter.ViewHolder holder, int position) {

        MealsItem mealsItem = mealsFavorite.get(position);
        holder.fav_tv_mealName.setText(mealsItem.getStrMeal());
        holder.fav_tv_mealArea.setText(mealsItem.getStrArea());

        Glide.with(viewGroup.getContext()).load(mealsItem.getStrMealThumb()).into(holder.fav_img_mealImg);

        /* Favorites Firestore + Room part 3/4: Loading in recycler view and Removing */

        NetworkChecker networkChecker = NetworkChecker.getInstance();




        holder.btn_removeFromFavorites.setOnClickListener(new View.OnClickListener() {        //\\\\\\\
            @Override
            public void onClick(View view) {


                if(!networkChecker.checkIfInternetIsConnected()){
                    MainActivity.mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.mainActivity, "Turn internet on to be able to remove meals from your favorites..", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else if (networkChecker.checkIfInternetIsConnected()){


                    progressDialog.show();



                    FirebaseFirestore.getInstance().collection("userFavorites").document(mealsItem.documentID)
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.i(TAG, "DocumentSnapshot successfully deleted!");
                                    //(FavoriteMealsAdapter.this).notifyDataSetChanged();

                                    rep=new Repository(viewGroup.getContext());
                                    rep.delete(mealsItem);
                                    mealsFavorite.remove(position);
                                    notifyDataSetChanged();

                                    progressDialog.dismiss();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.i(TAG, "Error deleting document", e);
                                }
                            });
                }


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

        /* Favorites Firestore + Room part 4/4: Getting item count */

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
