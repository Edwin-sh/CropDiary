package com.example.cropdiary.domain.user

import android.os.CountDownTimer
import com.example.cropdiary.data.model.UserModel
import com.example.cropdiary.data.repository.UserRepository
import javax.inject.Inject


class GetUserUseCase @Inject constructor(private val repository :UserRepository) {
    suspend operator fun invoke(email: String): Result<UserModel?> {
        return repository.getUser(email)
    }
}