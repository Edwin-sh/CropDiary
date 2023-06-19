package com.example.cropdiary.data.repository

import com.example.cropdiary.core.TasksHelper
import com.example.cropdiary.data.model.UserModel
import com.example.cropdiary.di.firebase.collections.UserDataCollection
import com.example.cropdiary.di.firebase.collections.UsersCollection
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val usersCollection: UsersCollection,
    private val userDataCollection: UserDataCollection,
    private val tasksHelper: TasksHelper
) : IUserRepository {

    /*fun createUser() {
        userRef.get().addOnSuccessListener { document ->
            if (!document.exists()) {
                userRef.set(hashMapOf<String, String>()).addOnSuccessListener {
                    Log.d("UserModel Controller", "Se creó el documento")
                }.addOnFailureListener { e ->
                    Log.e("UserModel Controller", "Ocurrió un error al crear el documento", e)
                }
            } else {
                Log.w("UserModel Controller", "Ya existía el documento")
            }
        }
    }*/
    override suspend fun createUser(userModel: UserModel): (Result<Boolean>) {
        return tasksHelper.getVoidResult(userDataCollection.getUserDocument(userModel.idEmail).set(userModel))
    }

    override suspend fun getUser(email: String): (Result<UserModel?>) {
        return tasksHelper.getDocumentResult(userDataCollection.getUserDocument(email).get())
    }
}