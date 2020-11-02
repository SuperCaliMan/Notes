package com.example.compose.home


import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.Text
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.example.compose.NoteViewModel
import com.example.compose.R
import com.example.compose.detail.Params
import com.example.compose.detail.StateScreen
import com.example.compose.ui.*
import com.supercaliman.domain.UiNote

@Composable
fun newHomeScreen(
    viewModel: NoteViewModel,
    navController: NavController,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val data: List<UiNote> by viewModel.uiLiveData.observeAsState(listOf())
    val errorData: Exception? by viewModel.errorStatus.observeAsState()
    val loading: Boolean by viewModel.loadingStatus.observeAsState(true)
    viewModel.getNotesList()

    errorData?.let {
        renderError(it)
    }


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            newAppBarHome()
        },
        drawerContent = {
            AppDrawer(navController = navController,
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
                val isDarkTheme = isDarkTheme(ContextAmbient.current)
                IconButton(
                    onClick = {
                        if (isDarkTheme) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        } else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    },
                    icon = {
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
                        navController.navigate(Screen.Detail.route + "/${StateScreen.INSERT}")
                    },
                    icon = { Icon(asset = Icons.Outlined.Edit) },
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
                            Screen.Detail.route + "/${StateScreen.READ}"
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
    val state = remember { mutableStateOf(false) }

    if (state.value) {
        showMessageDialog("Yep", "it's a new Feature 🎉", onClose = { state.value = false })
    }

    TopAppBar(
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 0.dp,
        title = {
            Text(
                stringResource(R.string.title_activity_main_compose),
                style = MaterialTheme.typography.h5
            )
        }
    )
}


