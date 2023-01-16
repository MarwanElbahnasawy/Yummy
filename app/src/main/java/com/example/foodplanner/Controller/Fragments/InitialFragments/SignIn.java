package com.example.foodplanner.Controller.Fragments.InitialFragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import com.example.foodplanner.Controller.Activities.MainActivity;
import com.example.foodplanner.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignIn extends Fragment {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private AppCompatButton button;
    private TextInputEditText et_email;
    private TextInputEditText et_password;
    private AppCompatButton btn_signInWithEmailAndPassword;
    private ProgressDialog loadingBar;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    private SignInButton googeSignIn;
    private ImageView img_eye;
    private TextView register;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //For google sign in
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(requireContext(), gso);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadingBar = new ProgressDialog(requireContext());
        register = view.findViewById(R.id.buttonSignIn);
        et_email = view.findViewById(R.id.et_email);
        et_password = view.findViewById(R.id.et_password);
        btn_signInWithEmailAndPassword = view.findViewById(R.id.btn_signInWithEmailAndPassword);
        googeSignIn = view.findViewById(R.id.btn_signInWithGoogle);
        // img_eye = view.findViewById(R.id.img_eye);

        // sharedPreferences to save status of onBoarding

        SharedPreferences sharedPref = requireContext().getSharedPreferences(
                "setting", Context.MODE_PRIVATE);
        sharedPref.edit().putBoolean("first_look", true).apply();


        //Signing in with email and password
        btn_signInWithEmailAndPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingBar.setTitle("Signing in");
                loadingBar.setMessage("Please wait while signing in");
                loadingBar.setCanceledOnTouchOutside(false);


                String email = et_email.getText().toString();
                String password = et_password.getText().toString();


                if ((!email.isEmpty()) && (!password.isEmpty())) {

                    loadingBar.show();

                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            loadingBar.dismiss();

                            if (task.isSuccessful()) {
                                Toast.makeText(requireContext(), "login successful", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(requireContext(), MainActivity.class);
                                startActivity(intent);
                            } else {
                                Exception exception = task.getException();
                                if (exception == null) {
                                    Toast.makeText(getContext(), "UnExpected error occurred", Toast.LENGTH_LONG).show();
                                } else {
                                    if (exception.getClass().equals(FirebaseAuthException.class)) {
                                        if (((FirebaseAuthException) exception).getErrorCode().equals("ERROR_USER_NOT_FOUND")) {
                                            Toast.makeText(getContext(), "User not found", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    } else if (exception.getClass().equals(FirebaseNetworkException.class)) {
                                        Toast.makeText(getContext(), "Network error", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }

                                }

                            }
                        }
                    });
                } else {
                    if (email.isEmpty()) {
                        Toast.makeText(getContext(), "Enter your email", Toast.LENGTH_SHORT).show();
                    } else if (password.isEmpty()) {
                        Toast.makeText(getContext(), "Enter your password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

       // button to skip to register fragment
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(SignInDirections.actionNavSignInToNavRegister());

            }
        });

        //Google sign in button
        googeSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signInGoogle();
            }
        });

        //eye (password visibility) image
//        img_eye.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (et_password.getTransformationMethod().getClass().getSimpleName() .equals("PasswordTransformationMethod")) {
//                    et_password.setTransformationMethod(new SingleLineTransformationMethod());
//                }
//                else {
//                    et_password.setTransformationMethod(new PasswordTransformationMethod());
//                }
//                et_password.setSelection(et_password.getText().length());
//            }
//        });

    }

    //For google sign in
    void signInGoogle() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
    }


    //callback of sign in with google request (for google sign in)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            if (task.isSuccessful()) {
                Toast.makeText(requireContext(), "Sign in was successful", Toast.LENGTH_SHORT).show();

                //Next 2 lines were used to link google sign in with firebase
                AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(task.getResult().getIdToken(), null);
                firebaseAuth.signInWithCredential(firebaseCredential);

                Intent intent = new Intent(requireContext(), MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(requireContext(), "Sign in failed", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
