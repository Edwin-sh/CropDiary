package com.example.cropdiary.di.firebase.collections

import com.example.cropdiary.core.FirestoreCollectionsHelper
import com.example.cropdiary.di.firebase.documents.UserDocumentReference
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

class CropsCollection @Inject constructor(userDocumentReference: UserDocumentReference) {
    fun getAllCrops(): Task<QuerySnapshot> {
        return collection.get()
    }

    val collection:CollectionReference=userDocumentReference.document.collection(FirestoreCollectionsHelper.CROPS_COLLECTION)
}