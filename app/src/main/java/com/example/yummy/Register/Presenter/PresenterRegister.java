package com.example.yummy.Register.Presenter;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



public class PresenterRegister {

    private static final String TAG = "PresenterFirebaseFirea";

    private InterfaceRegister interfaceRegister;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public PresenterRegister(InterfaceRegister interfaceRegister) {
        this.interfaceRegister = interfaceRegister;
    }

    public void createUserWithEmailAndPassword(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                interfaceRegister.onCompleteRegisterWithEmailAndPassword(task , task.isSuccessful());
            }
        });

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                interfaceRegister.onFailureRegisterWithEmailAndPassword(e);
            }
        });
    }
}
