package com.example.cropdiary.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cropdiary.data.model.UserModel
import com.example.cropdiary.domain.user.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val getUserUseCase :GetUserUseCase): ViewModel() {
    val userModel = MutableLiveData<Result<UserModel?>>()
    val userResultModel = MutableLiveData<Result<Boolean>?>()
    fun getUser(email: String) {
        viewModelScope.launch {
            val result = getUserUseCase(email)
            userModel.postValue(result)
        }
    }
}