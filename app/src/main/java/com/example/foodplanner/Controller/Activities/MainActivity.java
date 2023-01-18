package com.example.foodplanner.Controller.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.foodplanner.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    NavController navController;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                       break;
                   default:
                       bottomNavigationView.setVisibility(View.VISIBLE);
               }
            }
        });



    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}