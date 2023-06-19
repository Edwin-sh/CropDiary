@file:Suppress("DEPRECATION")

package com.example.cropdiary.core.view

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import com.example.cropdiary.R
import com.example.cropdiary.core.FragmentsConstants
import com.example.cropdiary.core.SharedPrefUserHelper
import com.example.cropdiary.ui.view.Auth.AuthActivity
import com.example.cropdiary.ui.view.Auth.RecoveryPasswordFragment
import com.example.cropdiary.ui.view.Auth.SignUpFragment
import com.example.cropdiary.ui.view.main.MainActivity
import com.example.cropdiary.ui.view.user.RegistreUserActivity

object NavigationAuthHelper {
    fun showAuthActivity(activity: Activity){
        activity.startActivity(
            Intent(activity, AuthActivity::class.java)
        )
        activity.finish()
    }
    fun showForgotPasswordFragment(fragment: Fragment) {
        fragment.requireFragmentManager().beginTransaction()
            .replace(R.id.fragContainerAuth, RecoveryPasswordFragment())
            .addToBackStack(FragmentsConstants.FRAGMENT_SIGN_IN)
            .commit()
    }

    fun showSignUpFragment(fragment: Fragment) {
        fragment.requireFragmentManager().beginTransaction()
            .replace(R.id.fragContainerAuth, SignUpFragment())
            .addToBackStack(FragmentsConstants.FRAGMENT_SIGN_IN)
            .commit()
    }

    fun showMainOrRegisterActivity(activity: Activity) {
        if (SharedPrefUserHelper.getUserPrefs(activity).isRegistered) {
            activity.startActivity(
                Intent(activity, MainActivity::class.java)
            )
            activity.finish()
        } else {
            showRegisterActivity(activity)
        }
    }

    fun showRegisterActivity(activity: Activity) {
        activity.startActivity(
            Intent(activity, RegistreUserActivity::class.java)
        )
        activity.finish()
    }
}