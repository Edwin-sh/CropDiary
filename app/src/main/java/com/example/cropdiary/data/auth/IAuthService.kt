package com.example.cropdiary.data.auth

import com.example.cropdiary.data.model.FirebaseUserModel
import com.example.cropdiary.data.model.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseUser

interface IAuthService {
    suspend fun signUpWithEmail(firebaseUserModel: FirebaseUserModel): Result<FirebaseUser?>

    suspend fun signInWithEmail(firebaseUserModel: FirebaseUserModel): Result<FirebaseUser?>

    suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser?>

    suspend fun recoveryPassword(email: String): Result<Boolean>

    suspend fun signOut(
        provider: ProviderType,
        googleSignInClient: GoogleSignInClient
    ): Result<Boolean>
}