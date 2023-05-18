package com.example.cropdiary.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cropdiary.data.model.UserModel
import com.example.cropdiary.domain.user.GetUserUseCase
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    val userModel = MutableLiveData<Result<UserModel?>>()
    val userResultModel = MutableLiveData<Result<Boolean>?>()
    private var getUserUseCase = GetUserUseCase()
    fun getUser(email: String) {
        viewModelScope.launch {
            val result = getUserUseCase(email)
            userModel.postValue(result)
        }
    }
}