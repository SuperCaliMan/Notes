package com.supercaliman.home

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.supercaliman.home.detail.DetailScreen
import com.supercaliman.home.detail.Params
import com.supercaliman.home.detail.StateScreenDetail
import com.supercaliman.home.home.HomeScreen
import com.supercaliman.home.home.NewHomeScreen
import com.supercaliman.home.ui.NoteTheme
import com.supercaliman.home.ui.Screen
import com.supercaliman.home.ui.isDarkTheme
import com.supercaliman.remoteparams.RemoteParamsVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi


@AndroidEntryPoint
class MainComposeActivity : AppCompatActivity(R.layout.compose_activity) {


    private val noteViewModel: NoteViewModel by viewModels()
    private val remoteParamsVM: RemoteParamsVM by viewModels()


    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            NoteTheme(isDarkTheme(this)) {
                val navController = rememberNavController()
                noteViewModel.getNotesList()
                NavHost(
                    navController,
                    startDestination = if (remoteParamsVM.getNewFeature()) Screen.NewHome.route else Screen.Home.route
                ) {
                    composable(Screen.Home.route) {
                        HomeScreen(
                            viewModel = noteViewModel,
                            navController = navController
                        )
                    }
                    composable(
                        Screen.Detail.route + "/{${Params.PANEL_MODE}}?${Params.NOTE}={${Params.NOTE}}",
                        arguments = Screen.Detail.params
                    ) {
                        DetailScreen(
                            noteViewModel,
                            it.arguments?.getSerializable(Params.PANEL_MODE) as StateScreenDetail,
                            backStack = {
                                navController.popBackStack()
                            },
                            uuid = it.arguments?.getString(Params.NOTE)
                        )
                    }
                    composable(Screen.NewHome.route) {
                        NewHomeScreen(
                            noteViewModel = noteViewModel,
                            navController = navController,
                        )
                    }
                    //composable(Screen.Animation.route) { chatScreen() }
                }
            }
        }
    }
}



