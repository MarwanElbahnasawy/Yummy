package com.example.yummy.MainActivity.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yummy.MainActivity.Presenter.PresenterMainActivity;
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
    private NetworkChecker networkChecker;
    public static MainActivity mainActivity;
    TextView tv_internetConnection;
    private static final String TAG = "MainActivity";
    private Timer timer;
    Boolean timerIsExists = false;
    PresenterMainActivity presenterMainActivity;



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
                        switch (item.getItemId()) {

                            case R.id.nav_dailyInspirations:
                                Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.nav_dailyInspirations);
                                return true;
                            case R.id.nav_search:
                                Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.nav_search);
                                return true;
                            case R.id.nav_favoriteMeals:
                                if (isLoginAsGuest == true) {
                                    Toast.makeText(MainActivity.this, "You must log in to access this feature", Toast.LENGTH_SHORT).show();
                                    return false;
                                } else {
                                    Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.nav_favoriteMeals);
                                    return true;
                                }

                            case R.id.nav_weekPlanner:
                                if (isLoginAsGuest == true) {
                                    Toast.makeText(MainActivity.this, "You must log in to access this feature", Toast.LENGTH_SHORT).show();
                                    return false;
                                } else {
                                    Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.nav_weekPlanner);
                                    return true;
                                }
                        }
                        return false;
                    }
                });

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                switch (destination.getId()) {
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
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {

                    isLoginAsGuest = false;

                    presenterMainActivity = new PresenterMainActivity(MainActivity.this);
                    presenterMainActivity.deleteTableRoom();

                    while (navController.popBackStack() == true) {
                    }

                    Toast.makeText(MainActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.nav_signIn);
                } else {
                    Toast.makeText(MainActivity.this, "Logging out failed", Toast.LENGTH_SHORT).show();
                }

            }
        });

        checkNetwork();




    }

    private void checkNetwork() {
        if(!networkChecker.checkIfInternetIsConnected()){
            tv_internetConnection.setVisibility(View.VISIBLE);
            tv_internetConnection.setText("No Internet Connection");
            tv_internetConnection.setBackgroundColor(getResources().getColor(R.color.red));
        }


        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build();

        ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!timerIsExists){
                            timerIsExists = true;

                            tv_internetConnection.setBackgroundColor(getResources().getColor(R.color.green));
                            tv_internetConnection.setText("Internet is back!");
                            timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {
                                            tv_internetConnection.setVisibility(View.GONE);
                                        }
                                    });

                                }
                            }, 5000);
                        }


                    }
                });

            }

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                if (timerIsExists){
                    timerIsExists = false;
                    timer.cancel();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_internetConnection.setVisibility(View.VISIBLE);
                        tv_internetConnection.setText("No Internet Connection");
                        tv_internetConnection.setBackgroundColor(getResources().getColor(R.color.red));
                    }
                });

            }


        };

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(ConnectivityManager.class);
        connectivityManager.requestNetwork(networkRequest, networkCallback);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }


}