package com.example.yummy.SignIn.Presenter;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.yummy.Utility.NetworkChecker;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class PresenterSignIn {
    InterfaceSignIn interfaceSignIn;
    private Context context;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private static final String TAG = "PresenterFirebaseFireau";
    private NetworkChecker networkChecker;


    public PresenterSignIn(InterfaceSignIn interfaceSignIn, Context context) {
        this.interfaceSignIn = interfaceSignIn;
        this.context = context;
    }

    public void signIn(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                interfaceSignIn.onCompleteSignInWithEmailAndPassword(task);

            }
        });
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                interfaceSignIn.onFailureSignInWithEmailAndPassword(e);
            }
        });
    }

    public void signInGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("86693954186-dqg19ueeebjmggpvi0bs13d576p6vjdt.apps.googleusercontent.com")
                .requestEmail()
                .build();
        GoogleSignInClient gsc = GoogleSignIn.getClient(context, gso);



        Intent signInIntent = gsc.getSignInIntent();


        interfaceSignIn.onCompleteSignInIntent(signInIntent, 1000);

    }

    public void respondToActivityResultOfGoogleSignIn(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            firebaseAuth.signInWithCredential(GoogleAuthProvider.getCredential(task.getResult().getIdToken(), null)).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    interfaceSignIn.onCompleteGoogleSignIn(task);
                }
            });

            firebaseAuth.signInWithCredential(GoogleAuthProvider.getCredential(task.getResult().getIdToken(), null)).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    interfaceSignIn.onFailureGoogleSignIn(e);
                }
            });
        }
    }
}