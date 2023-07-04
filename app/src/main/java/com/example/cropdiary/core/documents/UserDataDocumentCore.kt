package com.example.cropdiary.core.documents

import com.example.cropdiary.data.model.UserModel
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject

class UserDataDocumentCore @Inject constructor() {
    fun mapToUserModel(userDocumentSnapshot: DocumentSnapshot): UserModel? {
        if (userDocumentSnapshot.exists()) {
            return UserModel(userDocumentSnapshot.id,
                userDocumentSnapshot["photoProfile"].toString(),
                userDocumentSnapshot["name"].toString(),
                userDocumentSnapshot["lastname"].toString(),
                userDocumentSnapshot["phoneNumber"].toString(),
                userDocumentSnapshot["identificationNumber"].toString())
        }
        return null
    }
}