package com.example.yummy.SearchByIngredient.View;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yummy.MainActivity.View.MainActivity;
import com.example.yummy.Model.MealsItem;
import com.example.yummy.Model.RootMeal;
import com.example.yummy.Network.RetrofitClient;
import com.example.yummy.R;
import com.example.yummy.Repository.Model.RepositoryLocal;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsFromSpecificIngredientAdapter extends RecyclerView.Adapter<MealsFromSpecificIngredientAdapter.MyViewHolder> {
    List<MealsItem> meals;
    ViewGroup viewGroup;
    NavController navController;
    private NetworkChecker networkChecker = NetworkChecker.getInstance();
    String[] weekDays = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    ArrayAdapter<String> arrayAdapter;
    private static final String TAG = "MealsFromSpecificIngred";
    private ProgressDialog progressDialog;
    Boolean isAlreadyInFavorites;
    RepositoryLocal rep;

    public MealsFromSpecificIngredientAdapter(List<MealsItem> meals, NavController navController) {
        this.meals = meals;
        this.navController =navController;

    }

    @NonNull
    @Override
    public MealsFromSpecificIngredientAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewGroup =parent;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_meal_items, parent, false);
        return new MealsFromSpecificIngredientAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealsFromSpecificIngredientAdapter.MyViewHolder holder, int position) {
        Glide.with(holder.itemView).load(meals.get(position).getStrMealThumb()).into(holder.mealImage);
        holder.mealName.setText(meals.get(position).getStrMeal());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!networkChecker.checkIfInternetIsConnected()){
                    MainActivity.mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.mainActivity, "Turn internet on to view this item.", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else if (networkChecker.checkIfInternetIsConnected()){
                    RetrofitClient.getInstance().getMyApi().getMealById(Integer.parseInt(meals.get(position).getIdMeal()))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<RootMeal>() {
                                @Override
                                public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                                    // loading ui
                                }

                                @Override
                                public void onNext(@io.reactivex.rxjava3.annotations.NonNull RootMeal rootMeal) {
                                    MealsFromSpecificIngredient.searchTextInput.setText("");
                                    navController
                                            .navigate(MealsFromSpecificIngredientDirections.
                                                    actionMealByIngrediantFragmentToMealDeatailsFragment(rootMeal.getMeals().get(0)));
                                }

                                @Override
                                public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }



            }
        });

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
                                                       AlertDialog.Builder builder = new AlertDialog.Builder(viewGroup.getContext());
                                                       builder.setTitle("This item is already in your favorite meals list.");
                                                       builder.setMessage("Would you like to remove it?");
                                                       builder.setCancelable(true);

                                                       builder.setPositiveButton("Remove it.", (DialogInterface.OnClickListener) (dialog, which) -> {
                                                           if(!networkChecker.checkIfInternetIsConnected()){
                                                               MainActivity.mainActivity.runOnUiThread(new Runnable() {
                                                                   @Override
                                                                   public void run() {
                                                                       Toast.makeText(MainActivity.mainActivity, "Turn internet on to be able to remove meals from your favorites.", Toast.LENGTH_SHORT).show();
                                                                   }
                                                               });

                                                           } else if (networkChecker.checkIfInternetIsConnected()){
                                                               progressDialog = new ProgressDialog(viewGroup.getContext());
                                                               progressDialog.setTitle("Removing favorites");
                                                               progressDialog.setMessage("Please wait while removing the selected item from your favorite meals.");
                                                               progressDialog.setCanceledOnTouchOutside(true);
                                                               progressDialog.show();
                                                               FirebaseFirestore.getInstance().collection("userFavorites").document(mealsItemSelected.documentID)
                                                                       .delete()
                                                                       .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                           @Override
                                                                           public void onSuccess(Void aVoid) {
                                                                               progressDialog.dismiss();
                                                                               Toast.makeText(viewGroup.getContext(), "Item removed successfully", Toast.LENGTH_SHORT).show();
                                                                               Log.i(TAG, "DocumentSnapshot successfully deleted!");

                                                                               rep=new RepositoryLocal(viewGroup.getContext());
                                                                               rep.delete(mealsItemSelected);

                                                                           }
                                                                       })
                                                                       .addOnFailureListener(new OnFailureListener() {
                                                                           @Override
                                                                           public void onFailure(@NonNull Exception e) {
                                                                               progressDialog.dismiss();
                                                                               Toast.makeText(viewGroup.getContext(), "Item removal failed", Toast.LENGTH_SHORT).show();
                                                                               Log.i(TAG, "Error deleting document", e);
                                                                           }
                                                                       });
                                                           }

                                                       });
                                                       builder.setNegativeButton("Keep it.", (DialogInterface.OnClickListener) (dialog, which) -> {

                                                       });


                                                       AlertDialog alertDialog = builder.create();
                                                       alertDialog.show();

                                                   }
                                               } else {
                                                   Log.i(TAG, "Error getting documents.", task.getException());
                                               }
                                           }
                                       }
                );
    }

    private void uploadDataToFireStoreInFavorites(MealsItem mealsItem) {
        progressDialog = new ProgressDialog(viewGroup.getContext());
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

                        rep = new RepositoryLocal(viewGroup.getContext());
                        rep.insert(mealsItem, "NULL",  documentReference.getId());

                        progressDialog.dismiss();
                        Toast.makeText(viewGroup.getContext(), "Item added successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        progressDialog.dismiss();
                        Toast.makeText(viewGroup.getContext(), "Error adding the item", Toast.LENGTH_SHORT).show();

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
                                                       AlertDialog.Builder builder = new AlertDialog.Builder(viewGroup.getContext());
                                                       builder.setTitle("This item is already in your week plan on this day.");
                                                       builder.setMessage("Would you like to remove it?");
                                                       builder.setCancelable(true);
                                                       builder.setPositiveButton("Remove it.", (DialogInterface.OnClickListener) (dialog, which) -> {
                                                           if(!networkChecker.checkIfInternetIsConnected()){
                                                               MainActivity.mainActivity.runOnUiThread(new Runnable() {
                                                                   @Override
                                                                   public void run() {
                                                                       Toast.makeText(MainActivity.mainActivity, "Turn internet on to be able to remove meals from your week plan.", Toast.LENGTH_SHORT).show();
                                                                   }
                                                               });

                                                           } else if (networkChecker.checkIfInternetIsConnected()){
                                                               progressDialog = new ProgressDialog(viewGroup.getContext());
                                                               progressDialog.setTitle("Removing favorites");
                                                               progressDialog.setMessage("Please wait while removing the selected item from your favorite meals.");
                                                               progressDialog.setCanceledOnTouchOutside(true);
                                                               progressDialog.show();

                                                               FirebaseFirestore.getInstance().collection("userWeekPlan").document(mealsItemSelected.documentID)
                                                                       .delete()
                                                                       .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                           @Override
                                                                           public void onSuccess(Void aVoid) {
                                                                               progressDialog.dismiss();
                                                                               Toast.makeText(viewGroup.getContext(), "Item removed successfully", Toast.LENGTH_SHORT).show();
                                                                               Log.i(TAG, "DocumentSnapshot successfully deleted!");
                                                                               //(FavoriteMealsAdapter.this).notifyDataSetChanged();

                                                                               rep=new RepositoryLocal(viewGroup.getContext());
                                                                               rep.delete(mealsItemSelected);


                                                                           }
                                                                       })
                                                                       .addOnFailureListener(new OnFailureListener() {
                                                                           @Override
                                                                           public void onFailure(@NonNull Exception e) {
                                                                               progressDialog.dismiss();
                                                                               Toast.makeText(viewGroup.getContext(), "Item removal failed", Toast.LENGTH_SHORT).show();
                                                                               Log.i(TAG, "Error deleting document", e);
                                                                           }
                                                                       });
                                                           }




                                                       });
                                                       builder.setNegativeButton("Keep it.", (DialogInterface.OnClickListener) (dialog, which) -> {

                                                       });
                                                       AlertDialog alertDialog = builder.create();
                                                       alertDialog.show();

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
        progressDialog = new ProgressDialog(viewGroup.getContext());
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

                        rep = new RepositoryLocal(viewGroup.getContext());
                        rep.insert(mealsItem , weekDay, documentReference.getId());

                        progressDialog.dismiss();
                        Toast.makeText(viewGroup.getContext(), "Item added successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        progressDialog.dismiss();
                        Toast.makeText(viewGroup.getContext(), "Error while adding the item" , Toast.LENGTH_SHORT).show();

                    }
                });


    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mealName;
        CircleImageView mealImage;
        ImageButton btn_addToFavorites;
        AutoCompleteTextView autoCompleteTextView;
        TextInputLayout textInputLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName=itemView.findViewById(R.id.area_meal);
            mealImage=itemView.findViewById(R.id.areaMeal_image);
            btn_addToFavorites=itemView.findViewById(R.id.btn_add_favourite_search);
            autoCompleteTextView=itemView.findViewById(R.id.auto_complete_textview_search);
            textInputLayout = itemView.findViewById(R.id.text_input_layout);

            arrayAdapter = new ArrayAdapter<String>(viewGroup.getContext(), R.layout.list_weekdays  , weekDays);
            autoCompleteTextView.setAdapter(arrayAdapter);

        }
    }

}
