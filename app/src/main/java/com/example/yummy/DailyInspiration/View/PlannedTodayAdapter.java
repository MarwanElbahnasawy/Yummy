package com.example.yummy.DailyInspiration.View;

import android.app.ProgressDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yummy.Favorites.View.FavoriteMealsAdapter;
import com.example.yummy.MainActivity.View.MainActivity;
import com.example.yummy.Model.MealsItem;
import com.example.yummy.R;
import com.example.yummy.Repository.Model.RepositoryLocal;
import com.example.yummy.Utility.NetworkChecker;
import com.example.yummy.WeekPlanner.View.WeekPlanner;
import com.example.yummy.WeekPlanner.View.WeekPlannerDirections;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class PlannedTodayAdapter extends RecyclerView.Adapter<PlannedTodayAdapter.ViewHolder> {


    private ViewGroup viewGroup;
    private static final String TAG = "PlannedTodayAdapter";
    private ProgressDialog progressDialog;
    private List<MealsItem> mealsWeekPlanner = new ArrayList<>();
    private RepositoryLocal rep;
    public static PlannedTodayAdapter InstanceProvidingMeals;


    private PlannedTodayAdapter(List<MealsItem> mealsWeekPlanner) {
        this.mealsWeekPlanner = mealsWeekPlanner;
    }

    public static PlannedTodayAdapter getInstanceProvidingMeals(List<MealsItem> mealsWeekPlanner) {
        if (InstanceProvidingMeals == null) {
            InstanceProvidingMeals = new PlannedTodayAdapter(mealsWeekPlanner);
        }
        return InstanceProvidingMeals;
    }

    public static PlannedTodayAdapter getInstance() {
        return InstanceProvidingMeals;
    }

    @NonNull
    @Override
    public PlannedTodayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.viewGroup = parent;

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.row_week_planner, parent, false);          //\\\\\\\\\\
        PlannedTodayAdapter.ViewHolder viewHolder = new PlannedTodayAdapter.ViewHolder(itemView);
        Log.i(TAG, "onCreateViewHolder: ");

        progressDialog = new ProgressDialog(viewGroup.getContext());
        progressDialog.setTitle("Removing meal from week plan");
        progressDialog.setMessage("Please wait while removing the selected item from your week plan.");
        progressDialog.setCanceledOnTouchOutside(true);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlannedTodayAdapter.ViewHolder holder, int position) {

        MealsItem mealsItem = mealsWeekPlanner.get(position);
        holder.week_planner_tv_mealName.setText(mealsItem.getStrMeal());
        holder.week_planner_tv_mealArea.setText(mealsItem.getStrArea());

        Glide.with(viewGroup.getContext()).load(mealsItem.getStrMealThumb()).into(holder.week_planner_img_mealImg);

        NetworkChecker networkChecker = NetworkChecker.getInstance();



        holder.btn_removeWeekPlannerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!networkChecker.checkIfInternetIsConnected()) {
                    MainActivity.mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.mainActivity, "Turn internet on to be able to remove meals from your week plan.", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else if (networkChecker.checkIfInternetIsConnected()) {


                    progressDialog.show();


                    FirebaseFirestore.getInstance().collection("userWeekPlan").document(mealsItem.documentID)
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.i(TAG, "DocumentSnapshot successfully deleted!");


                                    rep = new RepositoryLocal(viewGroup.getContext());
                                    rep.delete(mealsItem);

                                    mealsWeekPlanner.remove(position);
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


                Navigation.findNavController(viewGroup).navigate(DailyInspirationsDirections.actionNavHomeToMealDeatailsFragment(mealsWeekPlanner.get(position)));


            }
        });

    }

    @Override
    public int getItemCount() {
        return mealsWeekPlanner.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView week_planner_tv_mealName;
        public TextView week_planner_tv_mealArea;
        public ImageView week_planner_img_mealImg;
        public ImageButton btn_removeWeekPlannerItem;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            week_planner_tv_mealName = itemView.findViewById(R.id.week_planner_tv_mealName);
            week_planner_tv_mealArea = itemView.findViewById(R.id.week_planner_tv_mealArea);
            week_planner_img_mealImg = itemView.findViewById(R.id.week_planner_img_mealImg);
            btn_removeWeekPlannerItem = itemView.findViewById(R.id.btn_removeWeekPlannerItem);
        }

    }

    public void mealAddedFromDailyInspirations(MealsItem meal) {
        mealsWeekPlanner.add(meal);
        notifyDataSetChanged();
    }

    public void mealRemovedFromDailyInspirations(MealsItem meal) {
        for (int i = 0; i < mealsWeekPlanner.size(); i++) {
            if (meal.getStrMeal().equals(mealsWeekPlanner.get(i).getStrMeal()))
                mealsWeekPlanner.remove(i);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        int size = mealsWeekPlanner.size();
        mealsWeekPlanner.clear();
        notifyItemRangeRemoved(0, size);
    }

}
