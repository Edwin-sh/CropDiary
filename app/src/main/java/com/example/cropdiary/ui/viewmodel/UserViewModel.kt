package com.example.cropdiary.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cropdiary.data.model.UserModel
import com.example.cropdiary.domain.user.CreateUserUseCase
import com.example.cropdiary.domain.user.GetDataUserUseCase
import com.example.cropdiary.domain.user.IsRegisteredUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase,
    private val isRegisteredUseCase: IsRegisteredUseCase,
    private val getDataUserUseCase: GetDataUserUseCase
) : ViewModel() {
    val userDataModel = MutableLiveData<Result<UserModel?>>()
    val userResultModel = MutableLiveData<Result<Boolean>>()
    val _progressbar = MutableLiveData<Boolean>()
    private val progressBarHelper=ProgressBarHelper(_progressbar)

    fun isRegistered(email: String){
        progressBarHelper.isLoading(true)
        viewModelScope.launch {
            val result= isRegisteredUseCase(email)
            progressBarHelper.isLoading(false)
            userResultModel.postValue(result)
        }
    }
    fun createUser(userModel: UserModel) {
        progressBarHelper.isLoading(true)
        viewModelScope.launch {
            val result= createUserUseCase(userModel)
            progressBarHelper.isLoading(false)
            userResultModel.postValue(result)
        }
    }
    fun getUser(email: String) {
        progressBarHelper.isLoading(true)
        viewModelScope.launch {
            val result= getDataUserUseCase(email)
            progressBarHelper.isLoading(false)
            userDataModel.postValue(result)
        }
    }
}