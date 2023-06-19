package com.example.cropdiary.core

import android.util.Log
import com.example.cropdiary.core.ExceptionsHelper.authException
import com.example.cropdiary.data.model.UserModel
import com.example.cropdiary.di.firebase.documents.UserDocument
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TasksHelper @Inject constructor(private val userDocument: UserDocument) {
    suspend fun getAuthResult(task: Task<AuthResult>): Result<FirebaseUser?> {
        return withContext(Dispatchers.IO) {
            try {
                Result.success(task.await().user)
            } catch (ex: Exception) {
                Result.failure<FirebaseUser>(authException(ex))
            }
        }
    }
    suspend fun getDocumentResult(task: Task<DocumentSnapshot>): Result<UserModel?> {
        return withContext(Dispatchers.IO) {
            try {
                val result = task.await()
                if (result.exists()) {
                    Result.success(userDocument.mapToUserModel(result))
                } else {
                    Result.success(null)
                }
            } catch (ex: Exception) {
                Log.e("UserModel Controller", "Error al obtener UserModel")
                Result.failure(ex)
            }
        }
    }

    suspend fun getVoidResult(task: Task<Void>): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            val completionDeferred = CompletableDeferred<Result<Boolean>>()
            try {
                task.addOnSuccessListener {
                    completionDeferred.complete(Result.success(true))
                }
                    .addOnFailureListener { exception ->
                        completionDeferred.complete(Result.failure(exception))
                    }
            } catch (ex: Exception) {
                completionDeferred.complete(Result.failure(ex))
            }
            completionDeferred.await()
        }
    }
}