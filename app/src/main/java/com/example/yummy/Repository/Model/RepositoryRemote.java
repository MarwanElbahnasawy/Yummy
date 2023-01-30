package com.example.yummy.Repository.Model;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.yummy.DailyInspiration.Presenter.InterfaceDailyInspirations;
import com.example.yummy.MainActivity.Presenter.InterfaceMain;
import com.example.yummy.MainActivity.View.MainActivity;
import com.example.yummy.Register.Presenter.InterfaceRegister;
import com.example.yummy.SearchByArea.Model.EachAreaModel;
import com.example.yummy.SearchByArea.Model.RootAreasList;
import com.example.yummy.SearchByArea.Presenter.InterfaceAllAreas;
import com.example.yummy.SearchByArea.Presenter.InterfaceMealFromSpecificArea;
import com.example.yummy.SearchByCategory.Models.EachCategoryModel;
import com.example.yummy.SearchByCategory.Models.RootCategoriesList;
import com.example.yummy.SearchByCategory.Presenter.InterfaceAllCategories;
import com.example.yummy.SearchByCategory.Presenter.InterfaceMealFromSpecificCategory;
import com.example.yummy.SearchByIngredient.Model.EachIngredientModel;
import com.example.yummy.SearchByIngredient.Model.RootIngredientsList;
import com.example.yummy.SearchByIngredient.Presenter.InterfaceAllIngredients;
import com.example.yummy.SearchByIngredient.Presenter.InterfaceMealFromSpecificIngredient;
import com.example.yummy.SearchByMeal.Model.RootMealsFromSingleLetter;
import com.example.yummy.SearchByMeal.Presenter.InterfaceAllMeals;
import com.example.yummy.SignIn.Presenter.InterfaceSignIn;
import com.example.yummy.Model.MealsItem;
import com.example.yummy.Model.RootMeal;
import com.example.yummy.Network.RetrofitClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RepositoryRemote {
    private InterfaceDailyInspirations interfaceDailyInspirations;
    private InterfaceRegister interfaceRegister;
    private InterfaceAllAreas interfaceAllAreas;
    private InterfaceMealFromSpecificArea interfaceMealFromSpecificArea;
    private InterfaceAllCategories interfaceAllCategories;
    private InterfaceMealFromSpecificCategory interfaceMealFromSpecificCategory;
    private InterfaceAllIngredients interfaceAllIngredients;
    private InterfaceMealFromSpecificIngredient interfaceMealFromSpecificIngredient;
    private InterfaceAllMeals interfaceAllMeals;
    private InterfaceSignIn interfaceSignIn;
    private InterfaceMain interfaceMain;
    private Context context;
    private static final String TAG = "RepositoryRemote";
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private RetrofitClient retrofitClient = RetrofitClient.getInstance();
    private List<MealsItem> meals = new ArrayList<>();
    private List<EachAreaModel> areas;
    private List<EachCategoryModel> categories;
    private List<EachIngredientModel> ingredients;


    public RepositoryRemote(InterfaceDailyInspirations interfaceDailyInspirations) {
        this.interfaceDailyInspirations = interfaceDailyInspirations;
    }

    public RepositoryRemote(InterfaceRegister interfaceRegister) {
        this.interfaceRegister = interfaceRegister;
    }

    public RepositoryRemote(InterfaceAllAreas interfaceAllAreas) {
        this.interfaceAllAreas = interfaceAllAreas;
    }

    public RepositoryRemote(InterfaceMealFromSpecificArea interfaceMealFromSpecificArea) {
        this.interfaceMealFromSpecificArea = interfaceMealFromSpecificArea;
    }

    public RepositoryRemote(InterfaceAllCategories interfaceAllCategories) {
        this.interfaceAllCategories = interfaceAllCategories;
    }

    public RepositoryRemote(InterfaceMealFromSpecificCategory interfaceMealFromSpecificCategory) {
        this.interfaceMealFromSpecificCategory = interfaceMealFromSpecificCategory;
    }

    public RepositoryRemote(InterfaceAllIngredients interfaceAllIngredients) {
        this.interfaceAllIngredients = interfaceAllIngredients;
    }

    public RepositoryRemote(InterfaceMealFromSpecificIngredient interfaceMealFromSpecificIngredient) {
        this.interfaceMealFromSpecificIngredient = interfaceMealFromSpecificIngredient;
    }

    public RepositoryRemote(InterfaceAllMeals interfaceAllMeals) {
        this.interfaceAllMeals = interfaceAllMeals;
    }

    public RepositoryRemote(InterfaceSignIn interfaceSignIn, Context context) {
        this.interfaceSignIn = interfaceSignIn;
        this.context = context;
    }

    public RepositoryRemote() {

    }

    public RepositoryRemote(InterfaceMain interfaceMain) {
        this.interfaceMain = interfaceMain;
    }

    public void getDailyInspirations() {

        String[] countriesList = {"Indian", "Italian", "Chinese", "French", "British"};
        String randomCountry = countriesList[(new Random()).nextInt(countriesList.length)];

        Observable<RootMeal> observableRandom1 = retrofitClient.getMyApi().getRootRandom();
        Observable<RootMeal> observableRandom2 = retrofitClient.getMyApi().getRootRandom();
        Observable<RootMeal> observableRandom3 = retrofitClient.getMyApi().getRootRandom();
        Observable<RootMeal> observableRandom4 = retrofitClient.getMyApi().getRootRandom();
        Observable<RootMeal> observableRandom5 = retrofitClient.getMyApi().getRootRandom();

        ArrayList<Observable<RootMeal>> arrayListObservablesRandomMeal = new ArrayList<>(Arrays.asList(observableRandom1, observableRandom2, observableRandom3, observableRandom4, observableRandom5));

        Observable<RootMeal> combinedObservable = Observable.merge(arrayListObservablesRandomMeal);

        combinedObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {

                            for (int i = 0; i < response.getMeals().size(); i++) {
                                meals.add(response.getMeals().get(i));
                            }

                        },

                        error -> {
                            interfaceDailyInspirations.responseOfDataOnFailure(error);
                        },
                        () -> {
                            interfaceDailyInspirations.responseOfDataOnSuccess(meals);
                        }
                );

    }

    public void createUserWithEmailAndPassword(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                interfaceRegister.onCompleteRegisterWithEmailAndPassword(task);
            }
        }) ;
    }

    public void getAllAreas() {
        Observable<RootAreasList> observableAreas = retrofitClient.getMyApi().getRootAreasList();
        observableAreas.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                response -> {
                    areas = response.getMeals();

                },
                error -> {
                    error.printStackTrace();
                },
                () -> {
                    interfaceAllAreas.responseOfDataOnSuccess(areas);
                }
        );
    }

    public void getMealFromSpecificArea(String areaSelected) {
        retrofitClient.getMyApi()
                .getMealsOfSelectedArea(areaSelected)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        next -> {

                            meals = next.getMeals();


                        },
                        error -> {
                            Log.i(TAG, "onViewCreated: " + error.getMessage());
                        },
                        () -> {
                            interfaceMealFromSpecificArea.responseOfDataOnSuccess(meals);
                        }
                );
    }

    public void getAllCategories() {
        Observable<RootCategoriesList> observableCategory = retrofitClient.getMyApi().getRootCategoriesList();

        observableCategory.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                response -> {
                    Log.i(TAG, "getAllCategories: " + response.getCategories());
                    categories = response.getCategories();
                    Log.i(TAG, "getAllCategories:---------------- " + categories.size());


                },
                error -> {
                    error.printStackTrace();
                },
                () -> {
                    interfaceAllCategories.responseOfDataOnSuccess(categories);
                }
        );
    }


    public void getMealFromSpecificCategory(String categorySelected) {
        retrofitClient.getMyApi()
                .getMealsOfSelectedCategory(categorySelected)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        next -> {
                            meals = next.getMeals();


                        },
                        error -> {
                            Log.i(TAG, "getCategoryMeal: " + error.getMessage());
                        },
                        () -> {
                            interfaceMealFromSpecificCategory.responseOfDataOnSuccess(meals);

                        }
                );
    }

    public void getAllIngredients() {
        Observable<RootIngredientsList> observableCategory = RetrofitClient.getInstance().getMyApi().getRootIngredientsList();

        observableCategory.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                response -> {
                    ingredients = response.getMeals();


                },
                error -> {
                    error.printStackTrace();
                },
                () -> {
                    interfaceAllIngredients.responseOfDataOnSuccess(ingredients);
                }
        );


    }


    public void getMealFromSpecificIngredient(String ingredientSelected) {
        RetrofitClient.getInstance().getMyApi()
                .getMealsOfSelectedIngredient(ingredientSelected)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        next -> {
                             meals = next.getMeals();

                        },
                        error -> {
                            Log.i(TAG, "onViewCreated: " + error.getMessage());
                        },
                        () -> {
                            interfaceMealFromSpecificIngredient.responseOfDataOnSuccess(meals);
                        }
                );
    }

    public void getAllMeals(Character s) {
        Observable<RootMealsFromSingleLetter> observable = RetrofitClient.getInstance().getMyApi().getRootMealsBySingleLetter(s.toString());
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                response -> {

                    if (response.getMeals() != null) {
                        meals = response.getMeals();

                    }

                },
                error -> {
                    error.printStackTrace();
                },
                () -> {
                    interfaceAllMeals.responseOfDataOnSuccess(meals);
                }
        );
    }

    public void signIn(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                interfaceSignIn.onCompleteSignInWithEmailAndPassword(task);

            }
        }) ;

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
            }) ;
        }
    }


    public void changeHeaderTitle() {
        MainActivity.tv_headerDrawer.setText(firebaseAuth.getCurrentUser().getEmail().split("@")[0]);
    }

    public void deleteDataForThisUser() {
        List<String> documentIDs = new ArrayList<>();
        getMealsToBeDeletedFromFavorites(documentIDs);
    }

    private void getMealsToBeDeletedFromFavorites(List<String> documentIDs) {

        FirebaseFirestore.getInstance().collection("userFavorites")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               if (task.isSuccessful()) {
                                                   for (QueryDocumentSnapshot document : task.getResult()) {

                                                       if (document.get("userEmail").equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                                                           //documentIDs.add(document.getId());
                                                           FirebaseFirestore.getInstance().collection("userFavorites").document(document.getId())
                                                                   .delete();
                                                       }
                                                   }

                                               } else {
                                                   Log.i(TAG, "Error loading documents from firestore to Room.", task.getException());
                                               }
                                           }
                                       }
                );

        FirebaseFirestore.getInstance().collection("userWeekPlan")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               if (task.isSuccessful()) {
                                                   for (QueryDocumentSnapshot document : task.getResult()) {

                                                       if (document.get("userEmail").equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                                                           FirebaseFirestore.getInstance().collection("userWeekPlan").document(document.getId())
                                                                   .delete();
                                                       }
                                                   }

                                                   interfaceMain.onFinishedDeletingItemsOfThisAccount();
                                               } else {
                                                   Log.i(TAG, "Error loading documents from firestore to Room.", task.getException());
                                               }
                                           }
                                       }
                );


    }

    public void deleteAccount() {
        firebaseAuth.getCurrentUser().delete();
        interfaceMain.onFinishedDeletingAccount();
    }
}
