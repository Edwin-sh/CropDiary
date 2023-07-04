package com.example.cropdiary.di.firebase.documents

import android.util.Log
import com.example.cropdiary.core.FirestoreCollectionsHelper
import com.example.cropdiary.core.TasksHelper
import com.example.cropdiary.core.documents.CropDataDocumentCore
import com.example.cropdiary.data.model.CropInfoModel
import com.example.cropdiary.di.firebase.collections.CropDataCollection
import com.example.cropdiary.di.firebase.collections.CropsCollection
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CropDocument @Inject constructor(
    private val cropsCollection: CropsCollection,
    private val cropDataDocument: CropDataDocument,
    private val cropDataDocumentCore: CropDataDocumentCore,
    private val tasksHelper: TasksHelper
) {
    fun newDocument(): DocumentReference {
        return cropsCollection.collection.document()
    }

    fun registreCropData(
        documentReference: DocumentReference,
        cropInfoModel: CropInfoModel
    ): Task<Void> {
        return cropDataDocument.getDataDocument(documentReference).set(cropInfoModel)
    }

    suspend fun getCropData(
        documentReference: DocumentReference
    ): Result<CropInfoModel?> {
        val result = tasksHelper.getDocumentResult(
            cropDataDocument.getDataDocument(documentReference).get()
        )
        return if (result.isSuccess) {
            Result.success(cropDataDocumentCore.mapToCropInfoModel(result.getOrNull()!!))
        } else {
            Result.failure(result.exceptionOrNull()!!)
        }
    }

    fun deleteCrop(documentReference: DocumentReference): Task<Void> {
        return documentReference.delete()
    }
}