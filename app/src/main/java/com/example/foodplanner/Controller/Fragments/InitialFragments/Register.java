package com.example.foodplanner.Controller.Fragments.InitialFragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.foodplanner.Model.Repository;
import com.example.foodplanner.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class Register extends Fragment {
    TextInputEditText signUp_email, signUp_password, confirmPassword;
    AppCompatButton register;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    View v;
    Repository rep;

    private ProgressDialog loadingBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        signUp_email = view.findViewById(R.id.signUp_email_edt);
        signUp_password = view.findViewById(R.id.signUp_password_edt);
        register = view.findViewById(R.id.register_button);
        confirmPassword = view.findViewById(R.id.signUp_Confirm_password_edt);
        loadingBar = new ProgressDialog(requireContext());

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadingBar.setTitle("Registering");
                loadingBar.setMessage("Please wait while registering");
                loadingBar.setCanceledOnTouchOutside(false);


                String email = signUp_email.getText().toString();
                String password = signUp_password.getText().toString();
                String confirm = confirmPassword.getText().toString();


                if ((!email.isEmpty()) && (!password.isEmpty()) && (password.equals(confirm))) {

                    loadingBar.show();

                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

                        loadingBar.dismiss();


                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if (user != null) {
                                updateUserData(user, email);
                                Toast.makeText(requireContext(), "Registration was successful", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(requireContext(), MainActivity.class);
//                                startActivity(intent);

                                rep=new Repository(requireContext());
                                rep.loadRoomFromFirestore(FirebaseAuth.getInstance().getCurrentUser().getEmail());

                                Navigation.findNavController(view).navigate(R.id.action_nav_register_to_nav_home);

                            }
                        } else {
                            Exception exception = task.getException();
                            if (exception == null) {
                                Toast.makeText(getContext(), "UnExpected error occurred", Toast.LENGTH_LONG).show();
                            } else if (exception.getClass().equals(FirebaseNetworkException.class)) {
                                Toast.makeText(getContext(), "Network error", Toast.LENGTH_LONG).show();

                            } else {
                                if (((FirebaseAuthException) exception).getErrorCode().equals("ERROR_WEAK_PASSWORD")) {

                                    Toast.makeText(getContext(), "Password should be at least 6 characters", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                                }

                            }


                        }

                    });
                } else {
                    if (email.isEmpty()) {
                        Toast.makeText(getContext(), "Enter your email", Toast.LENGTH_SHORT).show();
                    } else if (password.isEmpty()) {
                        Toast.makeText(getContext(), "Enter your password", Toast.LENGTH_SHORT).show();
                    } else if (!password.equals(confirm)) {
                        Toast.makeText(getContext(), "Password not identical", Toast.LENGTH_LONG).show();

                    }

                }

//                Intent intent = new Intent(requireContext(), MainActivity.class);
//                startActivity(intent);


            }

            private void updateUserData(FirebaseUser currentUser, String name) {
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build();

                currentUser.updateProfile(profileUpdates).addOnCompleteListener(task -> {


                    if (task.isSuccessful()) {

                    } else {
                        Exception exception = task.getException();
                        if (exception == null) {
                            Toast.makeText(getContext(), "UnExpected error occurred", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });

    }
}