package com.example.cropdiary.data.repository

import com.example.cropdiary.data.model.UserModel

interface IUserRepository {

    suspend fun createUser(userModel: UserModel): (Result<Boolean>)

    suspend fun getUser(email: String): (Result<UserModel?>)
}