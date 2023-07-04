package com.example.cropdiary.domain.auth

import com.example.cropdiary.core.TasksHelper
import com.example.cropdiary.data.auth.AuthService
import javax.inject.Inject

class RecoveryPasswordUseCase @Inject constructor(private val service :AuthService, private val tasksHelper: TasksHelper) {

    suspend operator fun invoke(email: String): Result<Boolean> {
        return tasksHelper.getVoidResult(service.recoveryPassword(email))
    }
}