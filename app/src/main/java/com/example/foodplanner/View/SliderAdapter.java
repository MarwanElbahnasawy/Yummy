package com.example.foodplanner.View;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.foodplanner.Controller.Fragments.MainFragments.HomeDirections;
import com.example.foodplanner.Model.MealsItem;
import com.example.foodplanner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {


    ViewGroup viewGroupOfMeal;

    private ProgressDialog loadingBar;

    private static final String TAG = "SliderAdapter";

    Boolean isAlreadyInFavorites;


    //private List<SliderItem> meals;
    private ViewPager2 viewPager2;
    List<MealsItem> meals = new ArrayList<>();

    public SliderAdapter(List<MealsItem> meals, ViewPager2 viewPager2) {

        this.meals = meals;
        this.viewPager2 = viewPager2;

    }


    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewGroupOfMeal = parent;
        return new SliderViewHolder(
                LayoutInflater.from(viewGroupOfMeal.getContext()).inflate(
                        R.layout.slide_item_container,
                        parent,
                        false)

        );

    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //holder.imageView.setImageResource(sliderItemList.get(position).getImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(viewGroupOfMeal).navigate(HomeDirections.actionNavHomeToMealDeatailsFragment(meals.get(position)));

            }
        });
        MealsItem mealsItem = meals.get(position);
        Glide.with(viewGroupOfMeal.getContext()).load(mealsItem.getStrMealThumb()).into(holder.imageView);
        holder.tv_mealName.setText(mealsItem.getStrMeal());

        //Bookmark button on click:
        holder.img_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkIfItemAlreadyExists(meals.get(position));



            }
        });

        // to make scrolling infinite part 2/2
        if (position == meals.size() - 2) {
            viewPager2.post(runnable);
        }
    }

    private void checkIfItemAlreadyExists(MealsItem mealsItemSelected) {
        isAlreadyInFavorites = false;
        FirebaseFirestore.getInstance().collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               if (task.isSuccessful()) {
                                                   for (QueryDocumentSnapshot document : task.getResult()) {
                                                       Log.i(TAG, document.getId() + " => " + document.getData());
                                                       Log.i(TAG, " 1: => " + document.get("mealName"));
                                                       Log.i(TAG, " 2: => " + mealsItemSelected.getStrMeal());
                                                        if(document.get("mealName").equals(mealsItemSelected.getStrMeal()) & document.get("userEmail").equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                                                             isAlreadyInFavorites = true;
                                                        }
                                                   }
                                                   if(!isAlreadyInFavorites){
                                                       uploadDataToFireStore(mealsItemSelected);
                                                   } else{
                                                       Toast.makeText(viewGroupOfMeal.getContext() , "This item is already in favorites", Toast.LENGTH_SHORT).show();
                                                   }
                                               } else {
                                                   Log.i(TAG, "Error getting documents.", task.getException());
                                               }
                                           }
                                       }
                );
        Log.i(TAG, " 3: => " + isAlreadyInFavorites.toString());
    }

    private void uploadDataToFireStore(MealsItem mealsItem) {
        loadingBar = new ProgressDialog(viewGroupOfMeal.getContext());
        loadingBar.setTitle("Adding to favorites");
        loadingBar.setMessage("Please wait while adding the selected item to your favorites.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();


        Map<String, Object> user = new HashMap<>();

        user.put("userID", FirebaseAuth.getInstance().getCurrentUser().getUid());
        user.put("userEmail", FirebaseAuth.getInstance().getCurrentUser().getEmail());
        user.put("mealName", mealsItem.getStrMeal());
        user.put("mealArea", mealsItem.getStrArea());
        user.put("mealImgURL", mealsItem.getStrMealThumb());
        user.put("timeAdded", System.currentTimeMillis());

        FirebaseFirestore.getInstance().collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        loadingBar.dismiss();
                        Toast.makeText(viewGroupOfMeal.getContext(), "Data added successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        loadingBar.dismiss();
                        Toast.makeText(viewGroupOfMeal.getContext(), "Error while uploading data: " + e.toString(), Toast.LENGTH_SHORT).show();

                    }
                });


    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView imageView;
        private TextView tv_mealName;
        private ImageView img_bookmark;

        SliderViewHolder(@NonNull View itemView) {

            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlide);
            tv_mealName = itemView.findViewById(R.id.tv_mealName);
            img_bookmark = itemView.findViewById(R.id.img_bookmark);
        }

    }

    // to make scrolling infinite part 1/2
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            meals.addAll(meals);
            notifyDataSetChanged();
        }
    };
}
