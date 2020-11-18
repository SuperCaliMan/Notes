package com.example.compose.home


import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.example.compose.NoteViewModel
import com.example.compose.detail.Params
import com.example.compose.detail.StateScreen
import com.example.compose.ui.Screen
import com.example.compose.ui.progressBar
import com.example.compose.ui.renderError
import com.supercaliman.domain.UiNote

@Composable
fun homeScreen(
    viewModel: NoteViewModel,
    navController: NavController,
) {
    val data: List<UiNote> by viewModel.uiLiveData.observeAsState(listOf())
    val errorData: Exception? by viewModel.errorStatus.observeAsState()
    val loading: Boolean by viewModel.loadingStatus.observeAsState(true)
    viewModel.getNotesList()

    errorData?.let {
        renderError(it)
    }


    Scaffold(
        topBar = {
            appBarHome()
        },
        floatingActionButton = {
            if (!loading) {
                ExtendedFloatingActionButton(
                    onClick = {
                        navController.navigate(Screen.Detail.route + "/${StateScreen.INSERT}")
                    },
                    icon = { Icon(asset = Icons.Outlined.Edit) },
                    text = { Text(text = "Compose") },
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