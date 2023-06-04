package com.example.cropdiary.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cropdiary.data.auth.ProviderType
import com.example.cropdiary.data.model.UserModel
import com.example.cropdiary.domain.auth.*
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInWithEmailUseCase :SignInWithEmailUseCase,
    private val signUpWithEmailUseCase :SignUpWithEmailUseCase,
    private val signInWithGoogleUseCase :SignInWithGoogleUseCase,
    private val recoveryPasswordUseCase :RecoveryPasswordUseCase,
    private val signOutUseCase :SignOutUseCase
): ViewModel() {
    val authSignInModel = MutableLiveData<Result<FirebaseUser?>>()
    val authSignUpModel = MutableLiveData<Result<FirebaseUser?>>()
    val authResultModel = MutableLiveData<Result<Boolean>>()


    fun signInWithEmail(userModel: UserModel) {
        viewModelScope.launch {
            var result: Result<FirebaseUser?>? = signInWithEmailUseCase(userModel)
            authSignInModel.postValue(result!!)
        }
    }

    fun signUpWithEmail(userModel: UserModel) {
        viewModelScope.launch {
            var result: Result<FirebaseUser?> = signUpWithEmailUseCase(userModel)
            authSignUpModel.postValue(result)
        }
    }

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            var result: Result<FirebaseUser?>? = signInWithGoogleUseCase(idToken)
            authSignInModel.postValue(result!!)
        }
    }

    fun recoveryPassword(email: String) {
        viewModelScope.launch {
            var result: Result<Boolean> = recoveryPasswordUseCase(email)
            authResultModel.postValue(result)
        }
    }

    fun signOut(
        provider: ProviderType,
        googleSignInClient: GoogleSignInClient
    ) {
        viewModelScope.launch {
            var result: Result<Boolean> = signOutUseCase(provider, googleSignInClient)
            authResultModel.postValue(result)
        }
    }
}