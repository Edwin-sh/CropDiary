package com.example.cropdiary.data.auth

import android.util.Log
import com.example.cropdiary.R
import com.example.cropdiary.data.model.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthService @Inject constructor(private val firebaseAuth: FirebaseAuth) : IAuthService {
    override suspend fun signUpWithEmail(userModel: UserModel): Result<FirebaseUser?> {
        return withContext(Dispatchers.IO) {
            try {
                val result: FirebaseUser? =
                    firebaseAuth.createUserWithEmailAndPassword(
                        userModel.idEmail, userModel.password
                    ).await().user
                Result.success(result)
            } catch (ex: Exception) {
                Result.failure<FirebaseUser>(authException(ex))
            }
        }
    }

    override suspend fun signInWithEmail(userModel: UserModel): Result<FirebaseUser?> {
        return withContext(Dispatchers.IO) {
            try {
                val result: FirebaseUser? =
                    firebaseAuth.signInWithEmailAndPassword(
                        userModel.idEmail, userModel.password
                    )
                        .await().user
                Result.success(result)
            } catch (ex: Exception) {
                Result.failure<FirebaseUser>(authException(ex))
            }
        }
    }

    override suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser?> {
        return withContext(Dispatchers.IO) {
            try {
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                val result: FirebaseUser? =
                    firebaseAuth.signInWithCredential(credential).await().user
                Result.success(result)
            } catch (ex: Exception) {
                Result.failure<FirebaseUser>(authException(ex))
            }
        }
    }

    override suspend fun recoveryPassword(email: String): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                firebaseAuth.sendPasswordResetEmail(email).await()
                Result.success(true)
            } catch (ex: Exception) {
                Result.failure(authException(ex))
            }
        }
    }

    override suspend fun signOut(
        provider: ProviderType,
        googleSignInClient: GoogleSignInClient
    ): Result<Boolean> {
        return try {
            //Firebase Auth SignOut
            firebaseAuth.signOut()
            if (provider == ProviderType.GOOGLE) {
                //SignOut Google
                googleSignInClient.signOut()
            }
            Result.success(true)
        } catch (ex: Exception) {
            Result.failure(authException(ex))
        }
    }

    private fun authException(exception: Exception): Exception {
        try {
            throw exception!!
        } catch (e: FirebaseAuthException) {
            val errorMessage = when (e.errorCode) {
                "ERROR_USER_NOT_FOUND" ->
                    R.string.there_is_no_user_with_this_email.toString()
                "ERROR_EMAIL_ALREADY_IN_USE" -> R.string.email_already_in_use.toString()
                "ERROR_WRONG_PASSWORD" -> R.string.wrong_password.toString()
                else -> {
                    Log.w("Tag", "errorCode: ${e.errorCode}")
                    "R.string"
                }
            }
            return Exception(errorMessage, exception)
        } catch (e: Exception) {
            return Exception(
                R.string.an_error_ocurred_authenticating_the_user.toString(),
                exception
            )
        }
    }
}