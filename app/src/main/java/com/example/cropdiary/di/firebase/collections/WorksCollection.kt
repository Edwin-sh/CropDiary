package com.example.cropdiary.di.firebase.collections

import com.example.cropdiary.core.FirestoreCollectionsHelper
import com.example.cropdiary.di.firebase.documents.UserDocumentReference
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import javax.inject.Inject

class WorksCollection @Inject constructor() {
    fun getCollection(documentReference: DocumentReference):CollectionReference{
        return documentReference.collection(FirestoreCollectionsHelper.WORKS_COLLECTION)
    }
}