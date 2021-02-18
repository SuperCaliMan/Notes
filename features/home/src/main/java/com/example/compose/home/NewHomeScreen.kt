package com.example.compose.home


import android.content.Intent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.example.compose.MainComposeActivity
import com.example.compose.NoteViewModel
import com.example.compose.R
import com.example.compose.detail.Params
import com.example.compose.detail.StateScreenDetail
import com.example.compose.domain.UiNote
import com.example.compose.ui.*
import com.supercaliman.navigation.Destinations
import com.supercaliman.navigation.NavUtils
import kotlinx.coroutines.InternalCoroutinesApi


@InternalCoroutinesApi
@Composable
fun newHomeScreen(
    noteViewModel: NoteViewModel,
    navController: NavController,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val data: List<UiNote> by noteViewModel.uiNoteList.observeAsState(listOf())
    val errorData: Exception? by noteViewModel.error.observeAsState()
    val loading: Boolean by noteViewModel.loader.observeAsState(true)
    val stateScreen: EventHome? by noteViewModel.homeState.observeAsState()
    noteViewModel.getNotesList()

    stateScreen?.let {
        when (it) {
            EventHome.LOGOUT -> {
                val activity = AmbientContext.current as MainComposeActivity
                activity.startActivity(
                    NavUtils.openScreen(
                        AmbientContext.current,
                        Destinations.LOGIN_SCREEN
                    ).apply {
                        flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                    })
                activity.overridePendingTransition(0, 0)
                activity.finish()
            }
        }
    }

    errorData?.let {
        renderError(it)
    }


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            newAppBarHome()
        },
        drawerContent = {

            AppDrawer(
                onLogout = {
                    noteViewModel.logout()
                },
                navController = navController,
                closeDrawer = { scaffoldState.drawerState.close() })
        },
        bottomBar = {
            BottomAppBar(
                cutoutShape = CircleShape
            ) {
                IconButton(onClick = {
                    scaffoldState.drawerState.open()
                }) {
                    Icon(Icons.Filled.Menu)
                }
                Spacer(Modifier.weight(1f, true))
                val isDarkTheme = isDarkTheme(AmbientContext.current)
                IconButton(
                    onClick = {
                        if (isDarkTheme) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        } else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    },
                    content = {
                        if (isDarkTheme) {
                            Icon(Icons.Default.Bedtime)
                        } else Icon(Icons.Outlined.Bedtime)

                    }
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            if (!loading) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Screen.Detail.route + "/${StateScreenDetail.INSERT}")
                    },
                    content = { Icon(imageVector = Icons.Outlined.Edit) },
                    backgroundColor = MaterialTheme.colors.surface,
                    contentColor = MaterialTheme.colors.secondary
                )
            }
        },
        bodyContent = {
            if (loading) {
                progressBar()
            } else {
                noteList(
                    items = data,
                    onItemClick = {
                        navController.navigate(
                            Screen.Detail.route + "/${StateScreenDetail.READ}"
                                    + "?${Params.NOTE}=${it.uuid}"
                        )
                    }
                )
            }
        }
    )
}


@Composable
fun newAppBarHome() {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 0.dp,
        title = {
            Text(
                stringResource(R.string.title_activity_main_compose),
                style = MaterialTheme.typography.h4
            )
        }
    )
}


