package com.example.cropdiary.data.repository

import com.example.cropdiary.data.model.CropModel
import com.example.cropdiary.data.model.WorkModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference

interface IWorkRepository {
    suspend fun createWork(workModel: WorkModel, documentReference: DocumentReference): Task<DocumentReference>
    suspend fun getAllWorks(): (Result<MutableList<WorkModel>>)
}