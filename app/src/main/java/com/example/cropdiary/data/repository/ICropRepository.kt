package com.example.cropdiary.data.repository

import com.example.cropdiary.data.model.CropModel
import com.example.cropdiary.data.model.UserModel

interface ICropRepository {
    suspend fun createCrop(cropModel: CropModel): (Result<Boolean>)
    suspend fun getAllCrops(): (Result<MutableList<CropModel>>)
}