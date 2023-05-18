package com.example.cropdiary.domain.auth

import com.example.cropdiary.data.auth.AuthService
import com.example.cropdiary.data.auth.ProviderType
import com.google.android.gms.auth.api.signin.GoogleSignInClient

class SignOutUseCase() {
    private val service = AuthService()

    suspend operator fun invoke(
        provider: ProviderType,
        googleSignInClient: GoogleSignInClient
    ): Result<Boolean> {
        return service.signOut(provider, googleSignInClient)
    }
}