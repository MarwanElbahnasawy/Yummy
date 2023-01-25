package com.example.yummy.Controller.Activities;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yummy.Model.Repository;
import com.example.yummy.R;
import com.example.yummy.Utility.NetworkChecker;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    public static NavController navController;
    public static BottomNavigationView bottomNavigationView;
    ImageView img_lotOut;
    public static Boolean isLoginAsGuest = false;
    private NetworkChecker networkChecker ;
    public static MainActivity mainActivity;
    TextView tv_internetConnection;
    private int timerInternetIsConnected;


    Repository rep;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_internetConnection = findViewById(R.id.tv_internetConnection);

        mainActivity = this;

        networkChecker = NetworkChecker.getInstance(this);

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
                            case R.id.nav_favoriteMeals:
                                if(isLoginAsGuest == true){
                                    Toast.makeText(MainActivity.this, "You must log in to access this feature", Toast.LENGTH_SHORT).show();
                                    return false;
                                } else{
                                    Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.nav_favoriteMeals);
                                    return true;
                                }

                            case R.id.nav_weekPlanner:
                                if(isLoginAsGuest == true){
                                    Toast.makeText(MainActivity.this, "You must log in to access this feature", Toast.LENGTH_SHORT).show();
                                    return false;
                                } else{
                                    Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.nav_weekPlanner);
                                    return true;
                                }
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
                   case R.id.mealDeatailsFragment:
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

                    isLoginAsGuest = false;

                    rep=new Repository(MainActivity.this);

                    new Thread(() -> rep.deleteTableRoom()).start();

                    while (navController.popBackStack() == true){}

                    Toast.makeText(MainActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment).navigate(R.id.nav_signIn);
                } else{
                    Toast.makeText(MainActivity.this, "Logging out failed", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Timer t = new Timer( );
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                if (!NetworkChecker.getInstance().checkIfInternetIsConnected()){
                    MainActivity.mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_internetConnection.setVisibility(View.VISIBLE);
                            tv_internetConnection.setText("No Internet Connection");
                            tv_internetConnection.setBackgroundColor(getResources().getColor(R.color.red));
                            timerInternetIsConnected = 5001;
                        }
                    });

                } else{
                    MainActivity.mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(timerInternetIsConnected > 0){
                                timerInternetIsConnected = timerInternetIsConnected - 500;
                                tv_internetConnection.setBackgroundColor(getResources().getColor(R.color.green));
                                tv_internetConnection.setText("Internet is back!");
                            } else{
                                tv_internetConnection.setVisibility(View.GONE);
                            }
                        }
                    });

                }

            }
        }, 1000,500);



    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }


}