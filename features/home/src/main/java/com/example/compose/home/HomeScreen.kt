package com.example.compose.home


import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.example.compose.NoteViewModel
import com.example.compose.R
import com.example.compose.detail.Params
import com.example.compose.detail.StateScreenDetail
import com.example.compose.domain.UiNote
import com.example.compose.ui.ProgressBar
import com.example.compose.ui.RenderError
import com.example.compose.ui.Screen
import kotlinx.coroutines.InternalCoroutinesApi


@InternalCoroutinesApi
@Composable
fun HomeScreen(
    viewModel: NoteViewModel,
    navController: NavController,
) {
    val data: List<UiNote> by viewModel.uiNoteList.observeAsState(listOf())
    val errorData: Exception? by viewModel.error.observeAsState()
    val loading: Boolean by viewModel.loader.observeAsState(true)
    viewModel.getNotesList()

    errorData?.let {
        RenderError(it)
    }


    Scaffold(
        topBar = {
            AppBarHome()
        },
        floatingActionButton = {
            if (!loading) {
                ExtendedFloatingActionButton(
                    onClick = {
                        navController.navigate(Screen.Detail.route + "/${StateScreenDetail.INSERT}")
                    },
                    icon = { Icon(Icons.Outlined.Edit, stringResource(R.string.edit)) },
                    text = { Text(text = "Compose") },
                    backgroundColor = MaterialTheme.colors.surface,
                    contentColor = MaterialTheme.colors.secondary
                )
            }
        },
        content = {
            if (loading) {
                ProgressBar()
            } else {
                NoteList(
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