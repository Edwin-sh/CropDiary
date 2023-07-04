package com.example.cropdiary.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cropdiary.data.model.WorkModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorksViewModel @Inject constructor():ViewModel(){
    private var _listWorks: MutableList<WorkModel> = ArrayList()
    val worksData = MutableLiveData<List<WorkModel>>()
    val worksResult = MutableLiveData<Result<Boolean>>()
    fun getListWork() {
        viewModelScope.launch {
            worksData.postValue(_listWorks)
        }
    }

    fun addWork(workModel: WorkModel) {
        viewModelScope.launch {
            _listWorks.add(workModel)
            worksResult.postValue(Result.success(true))
        }
    }

    fun deleteWork(workModel: WorkModel) {
        viewModelScope.launch {
            _listWorks.remove(workModel)
            worksResult.postValue(Result.success(true))
        }
    }
    fun clearListWork() {
        viewModelScope.launch {
            _listWorks.clear()
            getListWork()
        }
    }
}