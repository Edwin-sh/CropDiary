package com.example.cropdiary.domain.user

import com.example.cropdiary.data.model.UserModel
import com.example.cropdiary.data.repository.UserRepository
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(private val repository : UserRepository) {
    suspend operator fun invoke(userModel: UserModel): Result<Boolean> {
        return repository.createUser(userModel)
    }
}