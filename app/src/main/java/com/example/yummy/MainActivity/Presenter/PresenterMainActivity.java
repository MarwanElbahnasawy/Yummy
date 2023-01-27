package com.example.yummy.MainActivity.Presenter;

import android.content.Context;

import com.example.yummy.Repository.Model.RepositoryLocal;

public class PresenterMainActivity {
    Context context;
    RepositoryLocal repositoryLocal;

    public PresenterMainActivity(Context context) {
        this.context = context;
        repositoryLocal = new RepositoryLocal(context);
    }


    public void deleteTableRoom() {
        new Thread(() -> repositoryLocal.deleteTableRoom()).start();
    }
}
