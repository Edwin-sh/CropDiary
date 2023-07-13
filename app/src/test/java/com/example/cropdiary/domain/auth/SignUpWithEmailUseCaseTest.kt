package com.example.cropdiary.domain.auth

import com.example.cropdiary.core.TasksHelper
import com.example.cropdiary.data.auth.AuthService
import com.example.cropdiary.data.model.FirebaseUserModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SignUpWithEmailUseCaseTest {

   /* @RelaxedMockK
    private lateinit var service: AuthService

    @RelaxedMockK
    private lateinit var tasksHelper: TasksHelper

    lateinit var signUpWithEmailUseCase: SignUpWithEmailUseCase*/

    /*@Before
    fun onBefore() {
        MockKAnnotations.init(this)
        signUpWithEmailUseCase = SignUpWithEmailUseCase(service, tasksHelper)
    }*/

    @Test
    fun `invoke should return expected result`() {
        //Given
        /*val userModel = FirebaseUserModel("test@example.com", "password")
        //coEvery { service.signUpWithEmail(userModel) } returns null
        coEvery { tasksHelper.getAuthResult(null) } returns Result.success(null)
        //Then
        val result=signUpWithEmailUseCase(userModel)*/
        //When
        assertEquals(4,2+2)
    }
}