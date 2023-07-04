package com.example.cropdiary.domain.crop

import com.example.cropdiary.data.model.CropModel
import com.example.cropdiary.data.repository.CropRepository
import javax.inject.Inject


class GetAllCropsUseCase @Inject constructor(private val repository :CropRepository) {
    suspend operator fun invoke(): Result<MutableList<CropModel>> {
        return repository.getAllCrops()
    }
}