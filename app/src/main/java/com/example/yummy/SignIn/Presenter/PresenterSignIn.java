package com.example.yummy.SignIn.Presenter;

import android.content.Context;
import android.content.Intent;

import com.example.yummy.Repository.Model.RepositoryLocal;
import com.example.yummy.Repository.Model.RepositoryRemote;

public class PresenterSignIn {
    private InterfaceSignIn interfaceSignIn;
    private Context context;
    private static final String TAG = "PresenterFirebaseFireau";
    private RepositoryRemote repositoryRemote;
    private RepositoryLocal repositoryLocal;


    public PresenterSignIn(InterfaceSignIn interfaceSignIn, Context context) {
        this.interfaceSignIn = interfaceSignIn;
        this.context = context;
    }

    public PresenterSignIn(Context context) {
        this.context = context;
        repositoryLocal = new RepositoryLocal(context);
    }

    public void signIn(String email, String password) {

        repositoryRemote = new RepositoryRemote(interfaceSignIn, context);
        repositoryRemote.signIn(email, password);


    }

    public void signInGoogle() {

        repositoryRemote = new RepositoryRemote(interfaceSignIn, context);
        repositoryRemote.signInGoogle();

    }

    public void respondToActivityResultOfGoogleSignIn(int requestCode, int resultCode, Intent data) {

        repositoryRemote = new RepositoryRemote(interfaceSignIn, context);
        repositoryRemote.respondToActivityResultOfGoogleSignIn(requestCode, resultCode, data);


    }

}