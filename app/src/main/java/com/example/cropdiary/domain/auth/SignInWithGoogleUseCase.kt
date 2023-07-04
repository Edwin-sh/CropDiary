package com.example.cropdiary.domain.auth

import com.example.cropdiary.core.TasksHelper
import com.example.cropdiary.data.auth.AuthService
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class SignInWithGoogleUseCase @Inject constructor(private val service :AuthService, private val tasksHelper: TasksHelper) {
    suspend operator fun invoke(idToken: String): Result<FirebaseUser?> {
        return  tasksHelper.getAuthResult(service.signInWithGoogle(idToken))
    }
}