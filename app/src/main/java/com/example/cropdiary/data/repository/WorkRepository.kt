package com.example.cropdiary.data.repository

import com.example.cropdiary.core.FirestoreCollectionsHelper
import com.example.cropdiary.core.TasksHelper
import com.example.cropdiary.data.model.WorkModel
import com.example.cropdiary.di.firebase.collections.WorksCollection
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import javax.inject.Inject

class WorkRepository @Inject constructor(
    private val worksCollection: WorksCollection
):IWorkRepository {
    override suspend fun createWork(workModel: WorkModel, documentReference: DocumentReference): Task<DocumentReference> {
        return worksCollection.getCollection(documentReference).add(workModel)
    }

    override suspend fun getAllWorks(): Result<MutableList<WorkModel>> {
        TODO("Not yet implemented")
    }
}