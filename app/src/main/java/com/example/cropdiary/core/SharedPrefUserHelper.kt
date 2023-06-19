package com.example.cropdiary.core

import android.content.Context
import com.example.cropdiary.data.auth.ProviderType
import com.example.cropdiary.data.model.UserPrefsModel

object SharedPrefUserHelper {
    private const val FILE_KEY = "com.example.cropdiary.USER_PREFERENCE_FILE_KEY"
    private const val EMAIL_KEY = "email"
    private const val IS_REGISTERED_KEY = "is_register"
    private const val PROVIDER_KEY = "provider"

    fun addUserPrefs(
        context: Context,
        email: String?,
        provider: ProviderType?,
        isRegistered: Boolean?
    ) {
        val prefs = context.getSharedPreferences(
            FILE_KEY,
            Context.MODE_PRIVATE
        ).edit()
        if (email != null) {
            prefs.putString(EMAIL_KEY, email)
        }
        if (provider != null) {
            prefs.putString(
                PROVIDER_KEY,
                provider.toString()
            )
        }
        if (isRegistered != null) {
            prefs.putBoolean(IS_REGISTERED_KEY, isRegistered)
        }
        prefs.apply()
    }

    fun getUserPrefs(context: Context): UserPrefsModel {
        val prefs = context.getSharedPreferences(
            FILE_KEY,
            Context.MODE_PRIVATE
        )
        val email: String? = prefs.getString(EMAIL_KEY, null)
        val provider: String? = prefs.getString(PROVIDER_KEY, null)
        val isRegistered: Boolean = prefs.getBoolean(IS_REGISTERED_KEY, false)
        return UserPrefsModel(email, provider, isRegistered)
    }

    fun clearPrefs(context: Context) {
        val prefs = context.getSharedPreferences(
            FILE_KEY,
            Context.MODE_PRIVATE
        ).edit()
        prefs.clear()
        prefs.apply()
    }
}