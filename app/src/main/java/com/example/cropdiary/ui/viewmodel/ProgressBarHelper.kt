package com.example.cropdiary.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.android.awaitFrame

class ProgressBarHelper(private val liveData: MutableLiveData<Boolean>) {
    fun isLoading(boolean: Boolean){
        liveData.postValue(boolean)
    }
}