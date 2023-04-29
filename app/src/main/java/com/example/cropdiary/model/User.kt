package com.example.cropdiary.model

import androidx.annotation.NonNull

class User(
    @NonNull var idEmail: String,
    @NonNull val password: String,
    var photoProfile:String,
    var name: String,
    var lastname: String,
    var phoneNumber: String,
    var idCardNumber: String
){
    constructor(idEmail: String, password: String):this(idEmail, password,"","","","","")
}

