package com.example.compose
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.compose.detail.Params
import com.example.compose.detail.StateScreen
import com.example.compose.detail.detailScreen
import com.example.compose.home.homeScreen
import com.example.compose.ui.NoteTheme
import com.example.compose.ui.Routing.Companion.DETAIL_SCREEN
import com.example.compose.ui.Routing.Companion.HOME_SCREEN
import com.example.compose.ui.isDarkTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class MainComposeActivity : AppCompatActivity(R.layout.compose_activity) {

    val viewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteTheme(isDarkTheme(this)) {
                val navController = rememberNavController()
                val remoteParamsVM: RemoteParamsVM by viewModels()
                Timber.d("It's a new Feature: ${remoteParamsVM.getNewFeature()}")
                NavHost(navController, startDestination = HOME_SCREEN) {
                    composable(HOME_SCREEN) {
                        homeScreen(
                            viewModel = viewModel,
                            navController = navController
                        )
                    }
                    composable(
                        DETAIL_SCREEN + "/{${Params.PANEL_MODE}}",
                        arguments = listOf(
                            navArgument(Params.PANEL_MODE) {
                                type = NavType.EnumType(StateScreen::class.java)
                            })
                    ) {
                        detailScreen(
                            viewModel,
                            it.arguments?.getSerializable(Params.PANEL_MODE) as StateScreen,
                            backStack = {
                                navController.popBackStack()
                            },
                            note = viewModel.paramsNote
                        )
                    }
                }
            }
        }
    }
}



