package com.example.cropdiary.di.firebase.documents

import android.util.Log
import com.example.cropdiary.core.FirestoreCollectionsHelper
import com.example.cropdiary.di.firebase.collections.CropDataCollection
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject

class CropDataDocument @Inject constructor(
    private val cropDataCollection: CropDataCollection
) {
    fun getDataDocument(documentReference: DocumentReference): DocumentReference {
        return cropDataCollection.getCollection(documentReference)
            .document(FirestoreCollectionsHelper.DATA_INFO_DOCUMENT_KEY)
    }


}