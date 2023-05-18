package com.example.cropdiary.domain.user

import com.example.cropdiary.data.model.UserModel
import com.example.cropdiary.data.repository.UserRepository

class GetUserUseCase() {
    private val repository = UserRepository()

    suspend operator fun invoke(email: String): Result<UserModel?> {
        return repository.getUser(email)
    }
}