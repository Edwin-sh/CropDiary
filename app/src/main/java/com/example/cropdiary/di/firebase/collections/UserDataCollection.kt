package com.example.cropdiary.di.firebase.collections

import com.example.cropdiary.core.FirestoreCollectionsHelper
import com.google.firebase.firestore.DocumentReference
import javax.inject.Inject

class UserDataCollection @Inject constructor(private val usersCollection: UsersCollection) {
    fun getUserDocument(userId: String): DocumentReference {
        return usersCollection.collection.document(userId)
            .collection(FirestoreCollectionsHelper.DATA_USER_COLLECTION)
            .document(FirestoreCollectionsHelper.DATA_INFO_DOCUMENT_KEY)
    }
}