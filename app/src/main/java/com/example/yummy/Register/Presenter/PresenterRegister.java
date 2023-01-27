package com.example.yummy.Register.Presenter;



import android.content.Context;

import com.example.yummy.Repository.Model.RepositoryLocal;
import com.example.yummy.Repository.Model.RepositoryRemote;



public class PresenterRegister {

    private static final String TAG = "PresenterFirebaseFirea";

    private InterfaceRegister interfaceRegister;
    RepositoryRemote repositoryRemote;
    Context context;
    RepositoryLocal repositoryLocal;


    public PresenterRegister(InterfaceRegister interfaceRegister) {
        this.interfaceRegister = interfaceRegister;
    }

    public PresenterRegister(Context context) {

        this.context = context;
        repositoryLocal = new RepositoryLocal(context);

    }

    public void createUserWithEmailAndPassword(String email, String password) {
        repositoryRemote = new RepositoryRemote(interfaceRegister);
        repositoryRemote.createUserWithEmailAndPassword(email, password);
    }



}
