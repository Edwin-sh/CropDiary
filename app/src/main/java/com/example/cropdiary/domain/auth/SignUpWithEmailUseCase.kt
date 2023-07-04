package com.example.cropdiary.domain.auth

import com.example.cropdiary.core.TasksHelper
import com.example.cropdiary.data.auth.AuthService
import com.example.cropdiary.data.model.FirebaseUserModel
import com.example.cropdiary.data.model.UserModel
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class SignUpWithEmailUseCase @Inject constructor(private val service :AuthService, private val tasksHelper: TasksHelper) {
    suspend operator fun invoke(firebaseUserModel: FirebaseUserModel): Result<FirebaseUser?> {
        return tasksHelper.getAuthResult(service.signUpWithEmail(firebaseUserModel))
    }
}