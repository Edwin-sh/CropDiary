package com.example.cropdiary.di.auth

interface GoogleServerClientIdProvider {
    fun getGoogleServerClientId(): String
}