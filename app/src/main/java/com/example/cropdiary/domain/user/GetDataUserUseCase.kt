package com.example.cropdiary.domain.user

import com.example.cropdiary.core.TasksHelper
import com.example.cropdiary.core.documents.UserDataDocumentCore
import com.example.cropdiary.data.model.UserModel
import com.example.cropdiary.data.repository.UserRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject


class GetDataUserUseCase @Inject constructor(private val repository: UserRepository, private val userDataDocumentCore: UserDataDocumentCore, private val tasksHelper: TasksHelper) {
    suspend operator fun invoke(email: String): Result<UserModel?> {
        val result: Result<DocumentSnapshot?> = tasksHelper.getDocumentResult(repository.getUser(email))
        return if (result.isSuccess){
            val data = tasksHelper.getDocumentResult(repository.getDataUser(result.getOrNull()!!))
            if (data.isSuccess){
                Result.success(userDataDocumentCore.mapToUserModel(data.getOrNull()!!))
            }
            else{
                Result.failure(data.exceptionOrNull()!!)
            }
        }else{
            Result.failure(result.exceptionOrNull()!!)
        }
    }
}