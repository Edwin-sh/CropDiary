package com.example.cropdiary.core.auth

interface GoogleServerClientIdProvider {
    fun getGoogleServerClientId(): String
}