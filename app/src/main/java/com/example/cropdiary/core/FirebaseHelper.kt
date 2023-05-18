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
    fun getFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }

    fun getGoogleSignInClient(activity: Activity): GoogleSignInClient {
        return GoogleSignIn.getClient(activity, GoogleSignInOptions.DEFAULT_SIGN_IN)
    }

    fun getFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    const val GOOGLE_SERVER_CLIENT_ID =
        "318308638225-4t7bqu1b62v6b5sirb0mgcufk822c52j.apps.googleusercontent.com"
}