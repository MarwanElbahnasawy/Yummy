package com.example.foodplanner;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseInstance {
    // Singleton

    private static final FirebaseAuth AUTH_INSTANCE = FirebaseAuth.getInstance();

    static FirebaseAuth getFirebaseAutInstance() {
        return AUTH_INSTANCE;
    }


}
