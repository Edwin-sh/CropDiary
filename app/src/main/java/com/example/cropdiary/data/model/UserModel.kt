package com.example.cropdiary.data.model

import androidx.annotation.NonNull

data class UserModel(
    @NonNull var idEmail: String,
    var photoProfile: String? = null,
    var name: String? = null,
    var lastname: String? = null,
    var phoneNumber: String? = null,
    var idCardNumber: String? = null
)/*{
    constructor(
        @NonNull idEmail: String,
        @NonNull password: String
    ) : this(idEmail, password, "", "", "", "", "")
}*/

