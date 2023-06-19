package com.example.cropdiary.domain.user

import com.example.cropdiary.data.model.UserModel
import com.example.cropdiary.data.repository.UserRepository
import javax.inject.Inject

class IsRegisteredUseCase @Inject constructor(private val repository : UserRepository) {
    suspend operator fun invoke(email: String): Result<Boolean> {
        val result=repository.getUser(email)
        return if (result.isFailure){
             Result.failure(result.exceptionOrNull()!!)
        }else {
            if (result.getOrNull()!=null){
                Result.success(true)
            }else{
                Result.success(false)
            }
        }
    }
}