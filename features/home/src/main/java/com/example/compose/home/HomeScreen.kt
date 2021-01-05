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
import com.example.compose.detail.StateScreenDetail
import com.example.compose.domain.UiNote
import com.example.compose.ui.Screen
import com.example.compose.ui.progressBar
import com.example.compose.ui.renderError
import kotlinx.coroutines.InternalCoroutinesApi


@InternalCoroutinesApi
@Composable
fun homeScreen(
    viewModel: NoteViewModel,
    navController: NavController,
) {
    val data: List<UiNote> by viewModel.uiNoteList.observeAsState(listOf())
    val errorData: Exception? by viewModel.error.observeAsState()
    val loading: Boolean by viewModel.loader.observeAsState(true)
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
                        navController.navigate(Screen.Detail.route + "/${StateScreenDetail.INSERT}")
                    },
                    icon = { Icon(Icons.Outlined.Edit) },
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
                            Screen.Detail.route + "/${StateScreenDetail.READ}"
                                    + "?${Params.NOTE}=${it.uuid}"
                        )
                    }
                )
            }
        }
    )
}