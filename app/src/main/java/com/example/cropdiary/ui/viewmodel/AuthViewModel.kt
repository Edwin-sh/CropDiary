package com.example.cropdiary.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cropdiary.data.auth.ProviderType
import com.example.cropdiary.data.model.UserModel
import com.example.cropdiary.domain.auth.*
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    val authSignInModel = MutableLiveData<Result<FirebaseUser?>>()
    val authSignUpModel = MutableLiveData<Result<FirebaseUser?>>()
    val authResultModel = MutableLiveData<Result<Boolean>>()
    private var signInWithEmailUseCase = SignInWithEmailUseCase()
    private var signUpWithEmailUseCase = SignUpWithEmailUseCase()
    private var signInWithGoogleUseCase = SignInWithGoogleUseCase()
    private var recoveryPasswordUseCase = RecoveryPasswordUseCase()
    private var signOutUseCase = SignOutUseCase()

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