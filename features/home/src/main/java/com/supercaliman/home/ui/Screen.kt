package com.supercaliman.home.ui

import androidx.navigation.NavType
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.navArgument
import com.supercaliman.home.detail.Params
import com.supercaliman.home.detail.StateScreenDetail


sealed class Screen(val route: String, var params: List<NamedNavArgument>) {
    object Home : Screen("HOME", listOf())
    object Detail : Screen(
        "DETAIL", mutableListOf(
            navArgument(Params.PANEL_MODE) {
                type = NavType.EnumType(StateScreenDetail::class.java)
            },
            navArgument(Params.NOTE) {
                type = NavType.StringType
                nullable = true //optional parameter may not be pass forward
            })
    )

    object NewHome : Screen("NEW_HOME", listOf())
    //object Animation : Screen("ANIMATION", listOf())
}