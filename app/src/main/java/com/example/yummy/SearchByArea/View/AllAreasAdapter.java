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

import de.hdodenhof.circleimageview.CircleImageView;

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
         String area=eachAreaModel.getStrArea();
         if(area.equals("American")){
             holder.circleImageView.setImageResource(R.drawable.america);
         }
         else if(area.equals("British")){
             holder.circleImageView.setImageResource(R.drawable.british);
         }
         else if(area.equals("Canadian")){
             holder.circleImageView.setImageResource(R.drawable.canada);
         }
         else if(area.equals("Chinese")){
             holder.circleImageView.setImageResource(R.drawable.china);
         }
         else if(area.equals("Croatian")){
             holder.circleImageView.setImageResource(R.drawable.croatian);
         }
         else if(area.equals("Dutch")){
             holder.circleImageView.setImageResource(R.drawable.dutch);
         }
         else if(area.equals("Egyptian")){
             holder.circleImageView.setImageResource(R.drawable.egypt);
         }
         else if(area.equals("French")){
             holder.circleImageView.setImageResource(R.drawable.french);
         }
         else if(area.equals("Greek")){
             holder.circleImageView.setImageResource(R.drawable.greek);
         }
         else if(area.equals("Indian")){
             holder.circleImageView.setImageResource(R.drawable.indian);
         }
         else if(area.equals("Irish")){
             holder.circleImageView.setImageResource(R.drawable.croatian);
         }
         else if(area.equals("Jamaican")){
             holder.circleImageView.setImageResource(R.drawable.kenya);
         }
         else if(area.equals("Japanese")){
             holder.circleImageView.setImageResource(R.drawable.japan);
         }

         else if(area.equals("Kenyan")){
             holder.circleImageView.setImageResource(R.drawable.ken);
         }
         else if(area.equals("Malaysian")){
             holder.circleImageView.setImageResource(R.drawable.malaysian);
         }
         else if(area.equals("Mexican")){
             holder.circleImageView.setImageResource(R.drawable.mexico);
         }
         else if(area.equals("Polish")){
             holder.circleImageView.setImageResource(R.drawable.croatian);
         }
         else if(area.equals("Portuguese")){
             holder.circleImageView.setImageResource(R.drawable.portug);
         }
         else if(area.equals("Russian")){
             holder.circleImageView.setImageResource(R.drawable.russian);
         }
         else if(area.equals("Spanish")){
             holder.circleImageView.setImageResource(R.drawable.spani);
         }
         else if(area.equals("Thai")){
             holder.circleImageView.setImageResource(R.drawable.thia);
         }
         else if(area.equals("Tunisian")){
             holder.circleImageView.setImageResource(R.drawable.tunisian);
         }
         else if(area.equals("Turkish")){
             holder.circleImageView.setImageResource(R.drawable.turcia);
         }
         else if(area.equals("Unknown")){
             holder.circleImageView.setImageResource(R.drawable.unknown);
         }
         else if(area.equals("Vietnamese")){
             holder.circleImageView.setImageResource(R.drawable.vietname);
         }
         else if(area.equals("Moroccan")){
             holder.circleImageView.setImageResource(R.drawable.moroco);
         }
         else if(area.equals("Italian")){
             holder.circleImageView.setImageResource(R.drawable.italian);
         }



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
        CircleImageView circleImageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            country=itemView.findViewById(R.id.item_country);
            circleImageView=itemView.findViewById(R.id.country_image);
        }
    }
}
