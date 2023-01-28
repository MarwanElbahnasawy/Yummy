package com.example.yummy.MainActivity.Presenter;

import android.content.Context;

import com.example.yummy.Repository.Model.RepositoryLocal;
import com.example.yummy.Repository.Model.RepositoryRemote;

public class PresenterMainActivity {
    Context context;
    RepositoryLocal repositoryLocal;
    RepositoryRemote repositoryRemote;
    InterfaceMain interfaceMain;

    public PresenterMainActivity(Context context) {
        this.context = context;
        repositoryLocal = new RepositoryLocal(context);
    }

    public PresenterMainActivity(InterfaceMain interfaceMain) {
        this.interfaceMain = interfaceMain;
    }

    public PresenterMainActivity() {

    }


    public void deleteTableRoom() {
        new Thread(() -> repositoryLocal.deleteTableRoom()).start();
    }

    public void deleteAccountData() {
        repositoryRemote = new RepositoryRemote(interfaceMain);
        repositoryRemote.deleteDataForThisUser();
    }

    public void deleteAccount() {
        repositoryRemote = new RepositoryRemote();
        repositoryRemote.deleteAccount();
    }

    public void loadHeaderTitle() {
        repositoryRemote = new RepositoryRemote();
        repositoryRemote.changeHeaderTitle();
    }
}
