package com.example.cropdiary.core

import android.app.Activity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

object FirebaseHelper {

    /*fun provideFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }
    fun provideGoogleSignInClient(activity: Activity): GoogleSignInClient {
        return GoogleSignIn.getClient(activity, GoogleSignInOptions.DEFAULT_SIGN_IN)}*/
    fun getFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
}
