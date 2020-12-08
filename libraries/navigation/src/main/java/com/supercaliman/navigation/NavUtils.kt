package com.supercaliman.navigation

import android.content.Context
import android.content.Intent

object NavUtils {

    fun openScreen(context: Context, action: String): Intent =
        Intent().setClassName(context, action)
}