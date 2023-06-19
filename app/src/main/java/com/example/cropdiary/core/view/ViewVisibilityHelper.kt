package com.example.cropdiary.core.view

import android.view.View
import androidx.core.view.isVisible

object ViewVisibilityHelper {
    fun setVisibility(view: View, boolean: Boolean){
        view.isVisible=boolean
    }
}