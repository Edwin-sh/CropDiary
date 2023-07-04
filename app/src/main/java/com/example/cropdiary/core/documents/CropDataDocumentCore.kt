package com.example.cropdiary.core.documents

import android.util.Log
import com.example.cropdiary.data.model.CropInfoModel
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject

class CropDataDocumentCore @Inject constructor() {
    fun mapToCropInfoModel(cropDocumentSnapshot: DocumentSnapshot): CropInfoModel? {
        if (cropDocumentSnapshot.exists()) {
            return CropInfoModel(
                cropDocumentSnapshot["name"].toString(),
                cropDocumentSnapshot["description"].toString()
            )
        }
        return null
    }
}