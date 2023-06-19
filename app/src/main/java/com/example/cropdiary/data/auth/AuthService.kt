package com.example.cropdiary.data.auth

import com.example.cropdiary.core.ExceptionsHelper.authException
import com.example.cropdiary.core.TasksHelper
import com.example.cropdiary.data.model.FirebaseUserModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import javax.inject.Inject

class AuthService @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val tasksHelper: TasksHelper
) : IAuthService {
    override suspend fun signUpWithEmail(firebaseUserModel: FirebaseUserModel): Result<FirebaseUser?> {
        return tasksHelper.getAuthResult(
            firebaseAuth.createUserWithEmailAndPassword(
                firebaseUserModel.email, firebaseUserModel.password
            )
        )
    }

    override suspend fun signInWithEmail(firebaseUserModel: FirebaseUserModel): Result<FirebaseUser?> {
        return tasksHelper.getAuthResult(
            firebaseAuth.signInWithEmailAndPassword(
                firebaseUserModel.email, firebaseUserModel.password
            )
        )
    }

    override suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser?> {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        return tasksHelper.getAuthResult(firebaseAuth.signInWithCredential(credential))
    }

    override suspend fun recoveryPassword(email: String): Result<Boolean> {
        return tasksHelper.getVoidResult(firebaseAuth.sendPasswordResetEmail(email))
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
}