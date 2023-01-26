package com.example.yummy.Register.View;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.yummy.MainActivity;
import com.example.yummy.Register.Presenter.InterfaceRegister;
import com.example.yummy.Register.Presenter.PresenterRegister;
import com.example.yummy.R;
import com.example.yummy.Repository.Model.RepositoryLocal;
import com.example.yummy.Utility.NetworkChecker;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;


public class Register extends Fragment implements InterfaceRegister {
    TextInputEditText signUp_email, signUp_password, confirmPassword;
    AppCompatButton register;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    View view;
    private NetworkChecker networkChecker;
    private ProgressDialog loadingBar;
    private PresenterRegister presenterRegister;
    private String email;
    private static final String TAG = "Register";


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

        this.view = view;

        signUp_email = view.findViewById(R.id.signUp_email_edt);
        signUp_password = view.findViewById(R.id.signUp_password_edt);
        register = view.findViewById(R.id.register_button);
        confirmPassword = view.findViewById(R.id.signUp_Confirm_password_edt);
        loadingBar = new ProgressDialog(requireContext());
        networkChecker = NetworkChecker.getInstance();

        presenterRegister = new PresenterRegister(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = signUp_email.getText().toString();
                String password = signUp_password.getText().toString();
                String confirm = confirmPassword.getText().toString();

                if(!networkChecker.checkIfInternetIsConnected()){
                    Toast.makeText(MainActivity.mainActivity, "Turn internet on to be able to register.", Toast.LENGTH_SHORT).show();

                }else if (networkChecker.checkIfInternetIsConnected()){
                    loadingBar.setTitle("Registering");
                    loadingBar.setMessage("Please wait while registering");
                    loadingBar.setCanceledOnTouchOutside(false);

                    if ((!email.isEmpty()) && (!password.isEmpty()) && (password.equals(confirm))) {

                        loadingBar.show();

                        presenterRegister.createUserWithEmailAndPassword(email, password);


                    } else {
                        if (email.isEmpty()) {
                            Toast.makeText(getContext(), "Enter your email", Toast.LENGTH_SHORT).show();
                        } else if (password.isEmpty()) {
                            Toast.makeText(getContext(), "Enter your password", Toast.LENGTH_SHORT).show();
                        } else if (!password.equals(confirm)) {
                            Toast.makeText(getContext(), "Password not identical", Toast.LENGTH_SHORT).show();

                        }

                    }

                }




            }



        });

    }


    @Override
    public void onCompleteRegisterWithEmailAndPassword(Task<AuthResult> task) {
        loadingBar.dismiss();

        if (task.isSuccessful()) {

                Toast.makeText(requireContext(), "Registration was successful", Toast.LENGTH_SHORT).show();


            presenterRegister = new PresenterRegister(requireContext());
            presenterRegister.loadRoomFromFirestore(FirebaseAuth.getInstance().getCurrentUser().getEmail());

                Navigation.findNavController(view).navigate(R.id.action_nav_register_to_nav_home);


        } else {
            Exception exception = task.getException();
            if (exception == null) {
                Toast.makeText(getContext(), "UnExpected error occurred", Toast.LENGTH_SHORT).show();
            } else if (exception.getClass().equals(FirebaseNetworkException.class)) {
                Toast.makeText(getContext(), "Network error", Toast.LENGTH_SHORT).show();

            } else {
                if (((FirebaseAuthException) exception).getErrorCode().equals("ERROR_WEAK_PASSWORD")) {

                    Toast.makeText(getContext(), "Password should be at least 6 characters", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }


        }
    }

    @Override
    public void onFailureRegisterWithEmailAndPassword(Exception e) {
        Log.i(TAG, "onFailureRegisterWithEmailAndPassword: Registration request failed, error message --> " + e.toString());
    }


}