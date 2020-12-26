package com.example.compose
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.chat.chatScreen
import com.example.compose.detail.Params
import com.example.compose.detail.StateScreen
import com.example.compose.detail.detailScreen
import com.example.compose.home.homeScreen
import com.example.compose.home.newHomeScreen
import com.example.compose.ui.NoteTheme
import com.example.compose.ui.Screen
import com.example.compose.ui.isDarkTheme
import com.supercaliman.core.NoteViewModel
import com.supercaliman.remoteparams.RemoteParamsVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi


@AndroidEntryPoint
class MainComposeActivity : AppCompatActivity(R.layout.compose_activity) {


    private val viewModel: NoteViewModel by viewModels()
    private val remoteParamsVM: RemoteParamsVM by viewModels()


    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteTheme(isDarkTheme(this)) {
                val navController = rememberNavController()
                NavHost(
                    navController,
                    startDestination = if (remoteParamsVM.getNewFeature()) Screen.NewHome.route else Screen.Home.route
                ) {
                    composable(Screen.Home.route) {
                        homeScreen(
                            viewModel = viewModel,
                            navController = navController
                        )
                    }
                    composable(
                        Screen.Detail.route + "/{${Params.PANEL_MODE}}?${Params.NOTE}={${Params.NOTE}}",
                        arguments = Screen.Detail.params
                    ) {
                        detailScreen(
                            viewModel,
                            it.arguments?.getSerializable(Params.PANEL_MODE) as StateScreen,
                            backStack = {
                                navController.popBackStack()
                            },
                            uuid = it.arguments?.getString(Params.NOTE)
                        )
                    }
                    composable(Screen.NewHome.route) {
                        newHomeScreen(
                            viewModel = viewModel,
                            navController = navController
                        )
                    }
                    composable(Screen.Animation.route) { chatScreen() }
                }
            }
        }
    }
}



