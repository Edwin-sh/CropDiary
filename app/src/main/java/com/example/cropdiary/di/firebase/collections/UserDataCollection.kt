package com.example.cropdiary.di.firebase.collections

import com.example.cropdiary.core.FirestoreCollectionsHelper
import com.example.cropdiary.di.firebase.documents.UserDocumentReference
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import javax.inject.Inject

class UserDataCollection @Inject constructor(private val userDocumentReference: UserDocumentReference) {
    fun getCollection(documentReference: DocumentReference):CollectionReference{
        return documentReference.collection(FirestoreCollectionsHelper.DATA_USER_COLLECTION)
    }
    fun getCollection():CollectionReference{
        return userDocumentReference.document.collection(FirestoreCollectionsHelper.DATA_USER_COLLECTION)
    }


}