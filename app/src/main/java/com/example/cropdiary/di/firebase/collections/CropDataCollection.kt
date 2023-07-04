package com.example.cropdiary.di.firebase.collections

import com.example.cropdiary.core.FirestoreCollectionsHelper
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import javax.inject.Inject

class CropDataCollection @Inject constructor(private val cropsCollection: CropsCollection) {
    fun getCollection(documentReference: DocumentReference):CollectionReference {
        return documentReference.collection(FirestoreCollectionsHelper.DATA_CROP_COLLECTION)
    }
}