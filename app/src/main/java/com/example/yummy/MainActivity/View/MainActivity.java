package com.example.yummy.MainActivity.View;

import static com.example.yummy.R.string.deleteAcount;
import static com.example.yummy.R.string.turnOnInternent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
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

import com.example.yummy.DailyInspiration.View.PlannedTodayAdapter;
import com.example.yummy.MainActivity.Presenter.InterfaceMain;
import com.example.yummy.MainActivity.Presenter.PresenterMainActivity;
import com.example.yummy.R;
import com.example.yummy.Utility.NetworkChecker;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity implements InterfaceMain {

    public static NavController navController;
    public static BottomNavigationView bottomNavigationView;
    ImageView img_logOut;
    public static Boolean isLoginAsGuest = false;
    private NetworkChecker networkChecker;
    public static MainActivity mainActivity;
    TextView tv_internetConnection;
    private static final String TAG = "MainActivity";
    private Timer timer;
    Boolean timerIsExists = false;
    PresenterMainActivity presenterMainActivity;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    public static TextView tv_headerDrawer;
    Toast toastLogOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        networkChecker = NetworkChecker.getInstance(this);


        tv_internetConnection = findViewById(R.id.tv_internetConnection);

        mainActivity = this;


        img_logOut = findViewById(R.id.img_logOut);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        bottomNavigationView = findViewById(R.id.activity_main_bottom_navigation_view);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);



        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.nav_dailyInspirations:
                                while (MainActivity.navController.popBackStack() == true) {
                                }
                                Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.nav_dailyInspirations);
                                return true;
                            case R.id.nav_search:
                                while (MainActivity.navController.popBackStack() == true) {
                                }
                                Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.nav_search);
                                return true;
                            case R.id.nav_favoriteMeals:
                                if (isLoginAsGuest == true) {
                                    Toast.makeText(MainActivity.this, R.string.access, Toast.LENGTH_SHORT).show();
                                    return false;
                                } else {
                                    while (MainActivity.navController.popBackStack() == true) {
                                    }
                                    Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.nav_favoriteMeals);
                                    return true;
                                }

                            case R.id.nav_weekPlanner:
                                if (isLoginAsGuest == true) {
                                    Toast.makeText(MainActivity.this, R.string.must_login, Toast.LENGTH_SHORT).show();
                                    return false;
                                } else {
                                    while (MainActivity.navController.popBackStack() == true) {
                                    }
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
                    case R.id.nav_dailyInspirations:
                    case R.id.nav_search:
                    case R.id.nav_favoriteMeals:
                    case R.id.nav_weekPlanner:
                        bottomNavigationView.setVisibility(View.VISIBLE);
                        img_logOut.setVisibility(View.VISIBLE);


                        break;
                    default:
                        bottomNavigationView.setVisibility(View.GONE);
                        img_logOut.setVisibility(View.GONE);

                }
            }
        });

        img_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                


                if(navigationView.getVisibility() == View.GONE){
                    navigationView.setVisibility(View.VISIBLE);
                } else{
                    navigationView.setVisibility(View.GONE);
                }




            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                    switch (item.getItemId()) {
                        case R.id.drawerChangeAppLanguage:
                            navigationView.setVisibility(View.GONE);
                            drawerChangeAppLanguage();
                            break;

                        case R.id.drawerLogout:
                            navigationView.setVisibility(View.GONE);
                            drawerLogOut();
                            break;

                        case R.id.drawerDeleteAcount:
                            if(isLoginAsGuest){
                                Toast.makeText(MainActivity.this, R.string.needAcount, Toast.LENGTH_SHORT).show();
                            } else{
                                if (!networkChecker.checkIfInternetIsConnected()) {
                                    Toast.makeText(MainActivity.this, R.string.needAcount, Toast.LENGTH_SHORT).show();
                                } else{
                                    navigationView.setVisibility(View.GONE);
                                    drawerDeleteAccount();
                                }

                            }
                            
                            break;
                    }

                return false;
            }
        });



        View headerView = navigationView.getHeaderView(0);
        tv_headerDrawer = (TextView) headerView.findViewById(R.id.tv_headerDrawer);


    }

    private void drawerChangeAppLanguage() {
        if(Locale.getDefault().getDisplayLanguage().toString().equals("English")){
            setLocale("ar");
        }
          else{
            setLocale("en");
        }

        recreate();

    }
    public void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    private void drawerLogOut() {
        FirebaseAuth.getInstance().signOut();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {

            isLoginAsGuest = false;

            presenterMainActivity = new PresenterMainActivity((Context) this);
            presenterMainActivity.deleteTableRoom();

            while (navController.popBackStack() == true) {
            }
            
            toastLogOut = new Toast(this);
            toastLogOut.makeText(MainActivity.this, R.string.logout, Toast.LENGTH_SHORT).show();


            Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.nav_signIn);
        } else {
            Toast.makeText(MainActivity.this, R.string.loggingOut, Toast.LENGTH_SHORT).show();
        }
    }

    private void drawerDeleteAccount() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.WaitAreYouSure);
        builder.setTitle(R.string.DeleteAccount);
        builder.setCancelable(true);


        builder.setPositiveButton(R.string.sure, (DialogInterface.OnClickListener) (dialog, which) -> {
            if (!networkChecker.checkIfInternetIsConnected()) {
                MainActivity.mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.mainActivity, R.string.turnOnInternent, Toast.LENGTH_SHORT).show();
                    }
                });
            } else{
                presenterMainActivity = new PresenterMainActivity((InterfaceMain) this);
                presenterMainActivity.deleteAccountData();
            }
        });

        builder.setNegativeButton(R.string.keep, (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void checkNetwork() {
        if (!networkChecker.checkIfInternetIsConnected()) {
            tv_internetConnection.setVisibility(View.VISIBLE);
            tv_internetConnection.setText(R.string.noInternent);
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
                        if (!timerIsExists) {
                            timerIsExists = true;

                            tv_internetConnection.setBackgroundColor(getResources().getColor(R.color.green));
                            tv_internetConnection.setText(R.string.backInternet);
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
                if (timerIsExists) {
                    timerIsExists = false;
                    timer.cancel();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_internetConnection.setVisibility(View.VISIBLE);
                        tv_internetConnection.setText(R.string.noInternet);
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


    @Override
    public void onFinishedDeletingItemsOfThisAccount() {
        presenterMainActivity = new PresenterMainActivity((InterfaceMain) this);
        presenterMainActivity.deleteAccount();

    }

    @Override
    public void onFinishedDeletingAccount() {
        presenterMainActivity = new PresenterMainActivity((Context) this);
        presenterMainActivity.deleteTableRoom();

        PlannedTodayAdapter.getInstance().clear();

        while (navController.popBackStack() == true) {
        }

        Toast.makeText(mainActivity, R.string.deleteAcount, Toast.LENGTH_SHORT).show();

        Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.nav_signIn);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkNetwork();
    }
}