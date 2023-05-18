package com.example.cropdiary.data.model

import androidx.annotation.NonNull

data class UserModel(
    @NonNull var idEmail: String,
    @NonNull val password: String,
    var photoProfile: String,
    var name: String,
    var lastname: String,
    var phoneNumber: String,
    var idCardNumber: String
) {
    constructor(
        @NonNull idEmail: String,
        @NonNull password: String
    ) : this(idEmail, password, "", "", "", "", "")
}

