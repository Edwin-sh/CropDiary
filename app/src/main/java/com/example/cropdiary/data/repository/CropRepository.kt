package com.example.cropdiary.data.repository

import android.util.Log
import com.example.cropdiary.core.TasksHelper
import com.example.cropdiary.core.documents.CropDataDocumentCore
import com.example.cropdiary.data.model.CropInfoModel
import com.example.cropdiary.data.model.CropModel
import com.example.cropdiary.di.firebase.collections.CropDataCollection
import com.example.cropdiary.di.firebase.collections.CropsCollection
import com.example.cropdiary.di.firebase.documents.BasicDocument
import com.example.cropdiary.di.firebase.documents.CropDocument
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CropRepository @Inject constructor(
    private val basicDocument: BasicDocument,
    private val cropsCollection: CropsCollection,
    private val cropDocument: CropDocument,
    private val cropDataDocumentCore: CropDataDocumentCore,
    private val cropDataCollection: CropDataCollection,
    private val workRepository: WorkRepository,
    private val tasksHelper: TasksHelper
) : ICropRepository {
    override suspend fun createCrop(cropModel: CropModel): Result<Boolean> {
        val reference = cropDocument.newDocument()
        return try {
            val referenceResult = tasksHelper.getVoidResult(basicDocument.emptyDocument(reference))
            if (referenceResult.isSuccess) {
                if (tasksHelper.getVoidResult(
                        cropDocument.registreCropData(
                            reference,
                            cropModel.cropInfoModel
                        )
                    ).isSuccess
                ){
                    if (cropModel.worksList != null) {
                        for (work in cropModel.worksList!!) {
                            val workResult=tasksHelper.getDocumentReferenceBooleanResult(workRepository.createWork(work, reference))
                            if (workResult.isFailure){
                                return Result.failure(workResult.exceptionOrNull()!!)
                            }
                        }
                    }
                }
            }
            Result.success(true)
        } catch (ex: Exception) {
            cropDocument.deleteCrop(reference).await()
            Result.failure(ex)
        }
    }

    override suspend fun getAllCrops(): Result<MutableList<CropModel>> {
        return try {
            val list:MutableList<CropModel> =ArrayList()
            val crops: Result<List<DocumentSnapshot>> = tasksHelper.getQuerySnapshotResult(cropsCollection.getAllCrops())
            if (crops.isSuccess){
                val lista=crops.getOrNull()
                if (lista!=null && lista.isNotEmpty()){
                    for (value in lista) {
                        val document: Result<CropInfoModel?> =cropDocument.getCropData(value.reference)
                        list.add(CropModel(document.getOrNull()!!, null))
                    }
                }
            }
            Result.success(list)
        } catch (ex: Exception) {
            Log.w("Crops", "Error ${ex.cause}")
            Result.failure(ex)
        }
    }
}