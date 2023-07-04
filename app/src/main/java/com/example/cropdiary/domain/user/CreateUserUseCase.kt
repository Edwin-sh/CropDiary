package com.example.cropdiary.domain.user

import com.example.cropdiary.core.TasksHelper
import com.example.cropdiary.data.model.UserModel
import com.example.cropdiary.data.repository.UserRepository
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(private val repository : UserRepository, private val tasksHelper: TasksHelper) {
    suspend operator fun invoke(userModel: UserModel): Result<Boolean> {
        val result=tasksHelper.getVoidResult(repository.createUser())
        return if (result.isSuccess){
            tasksHelper.getVoidResult(repository.addDataUser(userModel))
        }else{
            Result.failure(result.exceptionOrNull()!!)
        }
    }
}