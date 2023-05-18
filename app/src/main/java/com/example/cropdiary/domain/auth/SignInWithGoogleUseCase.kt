package com.example.cropdiary.domain.auth

import com.example.cropdiary.data.auth.AuthService
import com.google.firebase.auth.FirebaseUser

class SignInWithGoogleUseCase() {
    private val service = AuthService()

    suspend operator fun invoke(idToken: String): Result<FirebaseUser?> {
        return service.signInWithGoogle(idToken)
    }
}