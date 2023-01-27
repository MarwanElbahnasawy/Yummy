package com.example.yummy.SearchByArea.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummy.MainActivity.View.MainActivity;
import com.example.yummy.SearchByArea.Model.EachAreaModel;
import com.example.yummy.R;
import com.example.yummy.Utility.NetworkChecker;

import java.util.List;

public class AllAreasAdapter extends RecyclerView.Adapter<AllAreasAdapter.MyViewHolder>{
    ViewGroup CountryView;
    List<EachAreaModel>  countries;
    private NetworkChecker networkChecker = NetworkChecker.getInstance();

    public AllAreasAdapter(List<EachAreaModel>  countries) {
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
        EachAreaModel eachAreaModel =countries.get(position);
        holder.country.setText(eachAreaModel.getStrArea());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!networkChecker.checkIfInternetIsConnected()){
                    MainActivity.mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.mainActivity, "Turn internet on to view meals related to this area.", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else if (networkChecker.checkIfInternetIsConnected()){
                    AllAreas.textInputEditText.setText("");
                    Navigation.findNavController(CountryView).navigate(AllAreasDirections.actionSearchBYCountryFragmentToMealByCountryFragment(countries.get(position).getStrArea()));

                }


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
