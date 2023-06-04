package com.example.cropdiary.domain.auth

import com.example.cropdiary.data.auth.AuthService
import com.example.cropdiary.data.model.UserModel
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class SignInWithEmailUseCase @Inject constructor (private val service :AuthService)  {
    suspend operator fun invoke(userModel: UserModel): Result<FirebaseUser?> {
        return service.signInWithEmail(userModel)
    }
}