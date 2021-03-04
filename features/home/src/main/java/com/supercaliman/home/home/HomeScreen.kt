package com.supercaliman.home.home


import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.supercaliman.home.NoteViewModel
import com.supercaliman.home.R
import com.supercaliman.home.detail.Params
import com.supercaliman.home.detail.StateScreenDetail
import com.supercaliman.home.domain.UiNote
import com.supercaliman.home.ui.ProgressBar
import com.supercaliman.home.ui.RenderError
import com.supercaliman.home.ui.Screen
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