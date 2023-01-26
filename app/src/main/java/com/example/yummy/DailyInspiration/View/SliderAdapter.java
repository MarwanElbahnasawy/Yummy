package com.example.yummy.DailyInspiration.View;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.yummy.MainActivity;
import com.example.yummy.Model.MealsItem;
import com.example.yummy.Repository.Model.RepositoryLocal;
import com.example.yummy.R;
import com.example.yummy.Utility.NetworkChecker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
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

    private ProgressDialog progressDialog;

    private static final String TAG = "SliderAdapter";

    Boolean isAlreadyInFavorites;

    RepositoryLocal rep;

    //For drop down weekdays:  part 1/3
    String[] weekDays = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    ArrayAdapter<String> arrayAdapter;

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
                Navigation.findNavController(viewGroupOfMeal).navigate(DailyInspirationsDirections.actionNavHomeToMealDeatailsFragment(meals.get(position)));

            }
        });
        MealsItem mealsItem = meals.get(position);
        Glide.with(viewGroupOfMeal.getContext()).load(mealsItem.getStrMealThumb()).into(holder.imageView);
        holder.tv_mealName.setText(mealsItem.getStrMeal());


        if(MainActivity.isLoginAsGuest == false){


        /* Favorites Firestore+Room part 1/4: Bookmark button */

        holder.btn_addToFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NetworkChecker networkChecker = NetworkChecker.getInstance();

                if(!networkChecker.checkIfInternetIsConnected()){
                    MainActivity.mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.mainActivity, "Turn internet on to be able to save meals to your favorites.", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else if (networkChecker.checkIfInternetIsConnected()){
                    checkIfItemAlreadyExistsInFavoritesOfFirestore(meals.get(position));
                }


            }
        });

        //For drop down weekdays: part 3/3
        /* WeekPlanner Firestore+Room part 1/4: Add button  */

        holder.autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int positionDay, long id) {

                NetworkChecker networkChecker = NetworkChecker.getInstance();

                if(!networkChecker.checkIfInternetIsConnected()){
                    MainActivity.mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.mainActivity, "Turn internet on to be able to save meals to your week plan.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else if (networkChecker.checkIfInternetIsConnected()){
                    String daySelected = parent.getItemAtPosition(positionDay).toString();
                    checkIfItemAlreadyExistsInWeekPlan(meals.get(position),daySelected);

                }

            }
        });



    }
        else if(MainActivity.isLoginAsGuest == true){
            holder.btn_addToFavorites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(viewGroupOfMeal.getContext(), "You need to log in to be able to save meals to your favorites.", Toast.LENGTH_SHORT).show();

                }
            });

            holder.autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int positionDay, long id) {
                Toast.makeText(viewGroupOfMeal.getContext(), "You need to log in to be able to save meals to your week plan.", Toast.LENGTH_SHORT).show();
            }
        });

       }





        // to make scrolling infinite part 2/2
        if (position == meals.size() - 2) {
            viewPager2.post(runnable);
        }




    }


    private void checkIfItemAlreadyExistsInFavoritesOfFirestore(MealsItem mealsItemSelected) {
        isAlreadyInFavorites = false;
        FirebaseFirestore.getInstance().collection("userFavorites")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               if (task.isSuccessful()) {
                                                   for (QueryDocumentSnapshot document : task.getResult()) {
                                                         if(document.get("strMeal").equals(mealsItemSelected.getStrMeal()) & document.get("userEmail").equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                                                             isAlreadyInFavorites = true;
                                                        }
                                                   }
                                                   if(!isAlreadyInFavorites){
                                                       uploadDataToFireStoreInFavorites(mealsItemSelected);
                                                   } else{
                                                       Toast.makeText(viewGroupOfMeal.getContext() , "This item is already in favorites", Toast.LENGTH_SHORT).show();
                                                   }
                                               } else {
                                                   Log.i(TAG, "Error getting documents.", task.getException());
                                               }
                                           }
                                       }
                );
    }

    private void uploadDataToFireStoreInFavorites(MealsItem mealsItem) {
        progressDialog = new ProgressDialog(viewGroupOfMeal.getContext());
        progressDialog.setTitle("Adding to favorites");
        progressDialog.setMessage("Please wait while adding the selected item to your favorite meals.");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();



        Map<String, Object> userFavorites = new HashMap<>();

        userFavorites.put("userID", FirebaseAuth.getInstance().getCurrentUser().getUid());
        userFavorites.put("userEmail", FirebaseAuth.getInstance().getCurrentUser().getEmail());
        userFavorites.put("timeAdded", System.currentTimeMillis());
        userFavorites.put("strMeal", mealsItem.getStrMeal());
        userFavorites.put("strArea", mealsItem.getStrArea());
        userFavorites.put("strMealThumb", mealsItem.getStrMealThumb());
        userFavorites.put("strYoutube", mealsItem.getStrYoutube());
        userFavorites.put("strInstructions", mealsItem.getStrInstructions());



        FirebaseFirestore.getInstance().collection("userFavorites")
                .add(userFavorites)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());

                        rep = new RepositoryLocal(viewGroupOfMeal.getContext());
                        rep.insert(mealsItem, "NULL",  documentReference.getId());

                        progressDialog.dismiss();
                        Toast.makeText(viewGroupOfMeal.getContext(), "Data added successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        progressDialog.dismiss();
                        Toast.makeText(viewGroupOfMeal.getContext(), "Error while uploading data: " + e.toString(), Toast.LENGTH_SHORT).show();

                    }
                });


    }



    private void checkIfItemAlreadyExistsInWeekPlan(MealsItem mealsItemSelected, String weekDay) {
        isAlreadyInFavorites = false;
        FirebaseFirestore.getInstance().collection("userWeekPlan")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               if (task.isSuccessful()) {
                                                   for (QueryDocumentSnapshot document : task.getResult()) {
                                                       mealsItemSelected.setWeekDay(weekDay);
                                                       mealsItemSelected.setCurrentUserEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());

                                                        if(document.get("strMeal").equals(mealsItemSelected.getStrMeal()) & document.get("userEmail").equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()) & document.get("weekDay").equals(weekDay)){
                                                           Log.i(TAG, "onComplete: here i am!!");
                                                           isAlreadyInFavorites = true;
                                                       }
                                                   }
                                                   if(!isAlreadyInFavorites){
                                                       uploadDataToFireStoreInWeekPlan(mealsItemSelected, weekDay);
                                                   } else{
                                                       Toast.makeText(viewGroupOfMeal.getContext() , "This item is already in the week plan on this day.", Toast.LENGTH_SHORT).show();

                                                   }
                                               } else {
                                                   Log.i(TAG, "Error getting documents.", task.getException());
                                               }
                                           }
                                       }
                );
        Log.i(TAG, " 3: => " + isAlreadyInFavorites.toString());
    }

    private void uploadDataToFireStoreInWeekPlan(MealsItem mealsItem, String weekDay) {
        progressDialog = new ProgressDialog(viewGroupOfMeal.getContext());
        progressDialog.setTitle("Adding to week plan");
        progressDialog.setMessage("Please wait while adding the selected item to your week plan.");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();


        Map<String, Object> userWeekPlan = new HashMap<>();

        userWeekPlan.put("userID", FirebaseAuth.getInstance().getCurrentUser().getUid());
        userWeekPlan.put("userEmail", FirebaseAuth.getInstance().getCurrentUser().getEmail());
        userWeekPlan.put("timeAdded", System.currentTimeMillis());
        userWeekPlan.put("strMeal", mealsItem.getStrMeal());
        userWeekPlan.put("strArea", mealsItem.getStrArea());
        userWeekPlan.put("strMealThumb", mealsItem.getStrMealThumb());
        userWeekPlan.put("strYoutube", mealsItem.getStrYoutube());
        userWeekPlan.put("strInstructions", mealsItem.getStrInstructions());
        userWeekPlan.put("weekDay", weekDay);



        FirebaseFirestore.getInstance().collection("userWeekPlan")
                .add(userWeekPlan)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());

                        rep = new RepositoryLocal(viewGroupOfMeal.getContext());
                        rep.insert(mealsItem , weekDay, documentReference.getId());

                        progressDialog.dismiss();
                        Toast.makeText(viewGroupOfMeal.getContext(), "Data added successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        progressDialog.dismiss();
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
        private ImageButton btn_addToFavorites;
        //For drop down weekdays: part 2/3
        AutoCompleteTextView autoCompleteTextView;
        TextInputLayout textInputLayout;

        SliderViewHolder(@NonNull View itemView) {

            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlide);
            tv_mealName = itemView.findViewById(R.id.tv_mealName);
            btn_addToFavorites = itemView.findViewById(R.id.btn_addToFavorites);


            //For drop down weekdays: part 2/3
            autoCompleteTextView = itemView.findViewById(R.id.auto_complete_textview);
            textInputLayout = itemView.findViewById(R.id.text_input_layout);
            arrayAdapter = new ArrayAdapter<String>(viewGroupOfMeal.getContext(), R.layout.list_weekdays  , weekDays);
            autoCompleteTextView.setAdapter(arrayAdapter);

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
