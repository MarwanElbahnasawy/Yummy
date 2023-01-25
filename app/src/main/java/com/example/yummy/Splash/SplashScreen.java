package com.example.yummy.Splash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.airbnb.lottie.LottieAnimationView;
import com.example.yummy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SplashScreen extends Fragment {

    private static final int timer = 4000;
    LottieAnimationView gifImageView;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    Intent intent;
    Handler handler;
    Runnable handlerRunnable;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_spash_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        gifImageView = view.findViewById(R.id.gif_splash);

        handler = new Handler();
        handlerRunnable = new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPref = requireContext().getSharedPreferences("setting", Context.MODE_PRIVATE);

                FirebaseUser user = firebaseAuth.getCurrentUser();
                Log.i("aaaaaaa", "run: " + firebaseAuth.getCurrentUser());
                boolean isFirst = sharedPref.getBoolean("first_look", false);

                if (!isFirst) {
                    Navigation.findNavController(view).navigate(R.id.action_navSpashScreen_to_navOnBoarding);

                } else if (user == null) {
                    Navigation.findNavController(view).navigate(R.id.action_navSpashScreen_to_navSignIn);

                } else {
//                    intent = new Intent(requireContext(), MainActivity.class);
//                    startActivity(intent);
//                    requireActivity().finish();
                    Navigation.findNavController(view).navigate(R.id.action_navSpashScreen_to_nav_home);

                }
            }
        };

        handler.postDelayed(handlerRunnable, timer);

    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(handlerRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(handlerRunnable, timer);
    }
}