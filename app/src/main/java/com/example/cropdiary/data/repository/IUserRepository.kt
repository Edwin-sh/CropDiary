package com.example.cropdiary.data.repository

import com.example.cropdiary.data.model.UserModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot

interface IUserRepository {

    suspend fun createUser(): Task<Void>
    suspend fun addDataUser(userModel: UserModel): Task<Void>
    suspend fun getUser(email: String): Task<DocumentSnapshot>

    suspend fun getDataUser(documentSnapshot: DocumentSnapshot): Task<DocumentSnapshot>
    suspend fun getAppUser(): (Result<Any?>)

}