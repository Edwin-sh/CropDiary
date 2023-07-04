package com.example.cropdiary.domain.auth

import com.example.cropdiary.core.TasksHelper
import com.example.cropdiary.data.auth.AuthService
import com.example.cropdiary.data.auth.ProviderType
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import javax.inject.Inject

class SignOutUseCase @Inject constructor(private val service :AuthService, private val tasksHelper: TasksHelper) {
    suspend operator fun invoke(
        provider: ProviderType,
        googleSignInClient: GoogleSignInClient
    ): Result<Boolean> {
        return try {
            service.signOut(provider, googleSignInClient)
            Result.success(true)
        }catch (ex:Exception){
            Result.failure(ex)
        }

    }
}