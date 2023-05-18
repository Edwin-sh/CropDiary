package com.example.cropdiary.data.repository

import com.example.cropdiary.data.model.UserModel

interface IUserRepository {

    suspend fun create(userUserModel: UserModel): UserModel

    suspend fun getUser(email: String): (Result<UserModel?>)
}