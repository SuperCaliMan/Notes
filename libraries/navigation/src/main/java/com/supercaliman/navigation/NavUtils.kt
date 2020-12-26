package com.supercaliman.navigation

import android.content.Context
import android.content.Intent

object NavUtils {

    fun openScreen(context: Context, destination: String): Intent =
        Intent().setClassName(context, destination)
}