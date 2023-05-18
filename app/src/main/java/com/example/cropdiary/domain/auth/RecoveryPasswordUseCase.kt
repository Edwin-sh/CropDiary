package com.example.cropdiary.domain.auth

import com.example.cropdiary.data.auth.AuthService

class RecoveryPasswordUseCase() {
    private val service = AuthService()

    suspend operator fun invoke(email: String): Result<Boolean> {
        return service.recoveryPassword(email)
    }
}