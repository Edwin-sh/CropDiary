package com.example.cropdiary.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cropdiary.data.model.CropModel
import com.example.cropdiary.domain.crop.CreateCropUseCase
import com.example.cropdiary.domain.crop.GetAllCropsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CropsViewModel @Inject constructor(
    private val getAllCropsUseCase: GetAllCropsUseCase,
    private val createCropUseCase: CreateCropUseCase,
    val worksViewModel: WorksViewModel
) : ViewModel() {
    val cropsData = MutableLiveData<Result<MutableList<CropModel>>>()
    val cropsResult = MutableLiveData<Result<Boolean>>()

    fun getDAta() {
        viewModelScope.launch {
            cropsData.postValue(getAllCropsUseCase.invoke())
        }
    }

    fun createCrop(cropModel: CropModel) {
        viewModelScope.launch {
            val result=createCropUseCase(cropModel)
            cropsResult.postValue(result)
        }
    }
}