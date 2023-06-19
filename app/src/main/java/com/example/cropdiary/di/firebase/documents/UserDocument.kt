package com.example.cropdiary.di.firebase.documents

import com.example.cropdiary.data.model.UserModel
import com.example.cropdiary.di.firebase.collections.UsersCollection
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject

class UserDocument @Inject constructor() {

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