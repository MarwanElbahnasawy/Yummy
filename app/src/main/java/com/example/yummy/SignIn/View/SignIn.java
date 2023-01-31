package com.example.yummy.SignIn.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.yummy.MainActivity.View.MainActivity;
import com.example.yummy.R;
import com.example.yummy.SignIn.Presenter.InterfaceSignIn;
import com.example.yummy.SignIn.Presenter.PresenterSignIn;
import com.example.yummy.Utility.NetworkChecker;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class SignIn extends Fragment implements InterfaceSignIn {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private AppCompatButton button;
    private TextInputEditText et_email;
    private TextInputEditText et_password;
    private AppCompatButton btn_signInWithEmailAndPassword;
    private ProgressDialog loadingBar;
    private SignInButton googeSignIn;
    private ImageView img_eye;
    private TextView register;
    private View view;
    private AppCompatButton btn_loginAsGuest;
    private PresenterSignIn presenterSignIn;
    private NetworkChecker networkChecker;

    private static final String TAG = "SignIn";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        this.view = view;

        loadingBar = new ProgressDialog(requireContext());
        register = view.findViewById(R.id.buttonSignIn);
        et_email = view.findViewById(R.id.et_email);
        et_password = view.findViewById(R.id.et_password);
        btn_signInWithEmailAndPassword = view.findViewById(R.id.btn_signInWithEmailAndPassword);
        googeSignIn = view.findViewById(R.id.btn_signInWithGoogle);
        btn_loginAsGuest = view.findViewById(R.id.btn_loginAsGuest);

        NetworkChecker networkChecker = NetworkChecker.getInstance();


        SharedPreferences sharedPref = requireContext().getSharedPreferences(
                "setting", Context.MODE_PRIVATE);
        sharedPref.edit().putBoolean("first_look", true).apply();

        presenterSignIn = new PresenterSignIn(this, requireContext());



        btn_signInWithEmailAndPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = et_email.getText().toString();
                String password = et_password.getText().toString();

                if (!networkChecker.checkIfInternetIsConnected()) {
                    Toast.makeText(MainActivity.mainActivity, "Turn internet on to be able to sign in.", Toast.LENGTH_SHORT).show();

                } else if (networkChecker.checkIfInternetIsConnected()) {
                    loadingBar.setTitle("Signing in");
                    loadingBar.setMessage("Please wait while signing in");
                    loadingBar.setCanceledOnTouchOutside(false);


                    if ((!email.isEmpty()) && (!password.isEmpty())) {

                        loadingBar.show();

                        presenterSignIn.signIn(email, password);


                    } else {
                        if (email.isEmpty()) {
                            Toast.makeText(getContext(), "Enter your email", Toast.LENGTH_SHORT).show();
                        } else if (password.isEmpty()) {
                            Toast.makeText(getContext(), "Enter your password", Toast.LENGTH_SHORT).show();
                        }
                    }

                }


            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(SignInDirections.actionNavSignInToNavRegister());


            }
        });


        googeSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!networkChecker.checkIfInternetIsConnected()) {
                    Toast.makeText(MainActivity.mainActivity, "Turn internet on to be able to sign in.", Toast.LENGTH_SHORT).show();

                } else if (networkChecker.checkIfInternetIsConnected()) {
                    presenterSignIn.signInGoogle();
                }


            }
        });

        btn_loginAsGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!networkChecker.checkIfInternetIsConnected()) {
                    Toast.makeText(MainActivity.mainActivity, "Turn internet on to be able to skip.", Toast.LENGTH_SHORT).show();

                } else if (networkChecker.checkIfInternetIsConnected()) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                    builder.setMessage("You 'll miss out on personalized content and saving our delicious recipes");
                    builder.setTitle("Wait! Are You Sure?");
                    builder.setCancelable(true);


                    builder.setPositiveButton("YES,I'M SURE", (DialogInterface.OnClickListener) (dialog, which) -> {
                        if (!networkChecker.checkIfInternetIsConnected()) {
                            MainActivity.mainActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.mainActivity, "Turn internet on to be able to skip.", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else if (networkChecker.checkIfInternetIsConnected()) {
                            MainActivity.isLoginAsGuest = true;
                            Toast.makeText(requireContext(), "Login as guest was successful.", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(view).navigate(SignInDirections.actionNavSignInToNavHome());
                        }


                    });

                    builder.setNegativeButton("NO,Go BACK", (DialogInterface.OnClickListener) (dialog, which) -> {

                        dialog.cancel();
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();


                }


            }
        });


    }


    @Override
    public void onCompleteSignInWithEmailAndPassword(Task<AuthResult> task) {
        loadingBar.dismiss();

        if (task.isSuccessful()) {
            Log.i(TAG, "onActivityResult: " + firebaseAuth.getCurrentUser().getEmail());

            Toast.makeText(requireContext(), "login successful", Toast.LENGTH_SHORT).show();


            Navigation.findNavController(view).navigate(R.id.action_navSignIn_to_nav_home);
        } else {
            Exception exception = task.getException();
            if (exception == null) {
                Toast.makeText(getContext(), "UnExpected error occurred", Toast.LENGTH_SHORT).show();
            } else {
                if (exception.getClass().equals(FirebaseAuthException.class)) {
                    if (((FirebaseAuthException) exception).getErrorCode().equals("ERROR_USER_NOT_FOUND")) {
                        Toast.makeText(getContext(), "User not found", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else if (exception.getClass().equals(FirebaseNetworkException.class)) {
                    Toast.makeText(getContext(), "Network error", Toast.LENGTH_SHORT).show();
                }
                else if (task.getException().getMessage().equals("There is no user record corresponding to this identifier. The user may have been deleted.")) {
                    Toast.makeText(getContext(), "User not found", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

        }
    }

    @Override
    public void onCompleteSignInIntent(Intent signInIntent, int i) {
        startActivityForResult(signInIntent, 1000);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK)
        {

                    if (data == null) {
                        Toast.makeText(requireContext(), "Intent is Null", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        presenterSignIn.respondToActivityResultOfGoogleSignIn(requestCode, resultCode, data);
                    }

            }


        }



    @Override
    public void onCompleteGoogleSignIn(Task<AuthResult> task) {
        if (task.isSuccessful()) {
            Toast.makeText(requireContext(), "Sign in with Google was successful", Toast.LENGTH_SHORT).show();

            Navigation.findNavController(view).navigate(R.id.action_navSignIn_to_nav_home);
        } else {
            Toast.makeText(requireContext(), "Sign in with google failed", Toast.LENGTH_SHORT).show();
        }
    }





}

