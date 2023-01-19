package com.example.foodplanner.Controller.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    NavController navController;
    BottomNavigationView bottomNavigationView;
    ImageView img_lotOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img_lotOut = findViewById(R.id.img_logOut);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        bottomNavigationView = findViewById(R.id.activity_main_bottom_navigation_view);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch(item.getItemId())
                        {

                            case R.id.nav_home:
                                Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.nav_home);
                                return true;
                            case R.id.nav_search:
                                Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.nav_search);
                                return true;
                            case R.id.nav_savedMeals:
                                Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.nav_savedMeals);
                                return true;
                            case R.id.nav_weekPlanner:
                                Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.nav_weekPlanner);
                                return true;
                            case R.id.mealDeatailsFragment:
                                Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.mealDeatailsFragment);
                                return true;
                        }
                        return false;
                    }
                });

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
               switch (destination.getId()){
                   case R.id.nav_spashScreen:
                   case R.id.nav_onBoarding:
                   case R.id.nav_signIn:
                   case R.id.nav_register:
                       bottomNavigationView.setVisibility(View.GONE);
                       img_lotOut.setVisibility(View.GONE);

                       break;
                   default:
                       bottomNavigationView.setVisibility(View.VISIBLE);
                       img_lotOut.setVisibility(View.VISIBLE);
               }
            }
        });

        img_lotOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                if(FirebaseAuth.getInstance().getCurrentUser() == null){

                    while (navController.popBackStack() == true){}

                    Toast.makeText(MainActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.nav_signIn);
                } else{
                    Toast.makeText(MainActivity.this, "Logging out failed", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}