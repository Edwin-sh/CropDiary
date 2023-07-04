package com.example.cropdiary.domain.crop

import com.example.cropdiary.data.model.CropModel
import com.example.cropdiary.data.repository.CropRepository
import javax.inject.Inject


class CreateCropUseCase @Inject constructor(private val repository :CropRepository) {
    suspend operator fun invoke(cropModel: CropModel): Result<Boolean> {
        return repository.createCrop(cropModel)
    }
}