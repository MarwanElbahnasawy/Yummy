package com.example.yummy.SignIn.Presenter;

import android.content.Intent;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public interface InterfaceSignIn {
    void onCompleteSignInWithEmailAndPassword(Task<AuthResult> task);

    void onCompleteGoogleSignIn(Task<AuthResult> task);

    void onCompleteSignInIntent(Intent signInIntent, int i);

    void onFailureSignInWithEmailAndPassword(Exception e);

    void onFailureGoogleSignIn(Exception e);
}
