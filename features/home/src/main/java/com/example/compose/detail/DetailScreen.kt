package com.example.compose.detail

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.compose.NoteViewModel
import com.example.compose.R
import com.example.compose.domain.UiNote
import com.example.compose.ui.Dimension
import com.example.compose.ui.progressBar

interface OnSaveListener {
    fun onSave(title: String, description: String)
}

object Params {
    const val PANEL_MODE = "500"
    const val NOTE = "501"
}


@Composable
fun detailScreen(
    viewModel: NoteViewModel,
    initialStateDetail: StateScreenDetail,
    backStack: () -> Unit,
    uuid: String?
) {
    val loading: Boolean by viewModel.loader.observeAsState(false)
    val note: UiNote? = viewModel.getNote(uuid)
    val state = remember { mutableStateOf(initialStateDetail) }


    val onSaveObj = object : OnSaveListener {
        override fun onSave(title: String, description: String) {
            viewModel.createNote(note?.uuid, title = title, description)
        }
    }


    Scaffold(
        topBar = {
            appBar(viewModel, state, note, backStack,
                onSave = {
                    viewModel.saveNote()
                    backStack()
                })
        },
        bodyContent = {
            if (loading) {
                progressBar()
            } else {
                when (state.value) {
                    StateScreenDetail.READ -> {
                        bodyDetail(true, note, onSaveObj)
                    }
                    StateScreenDetail.INSERT -> bodyDetail(false, null, onSaveObj)
                    StateScreenDetail.EDIT -> {
                        bodyDetail(false, note, onSaveObj)
                    }
                }
            }
        }
    )
}


@Composable
fun bodyDetail(
    isReadable: Boolean = false,
    note: UiNote?,
    onSaveListener: OnSaveListener
) {
    val title =
        remember { mutableStateOf(TextFieldValue(text = if (note?.title != null) note.title else "")) }
    val description =
        remember { mutableStateOf(TextFieldValue(if (note?.description != null) note.description else "")) }
    onSaveListener.onSave(title.value.text, description.value.text)

    val isInvalid = title.value.text.count() > 10

    ScrollableColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = Dimension.defaultMargin),
    ) {

        if (!isReadable) {
            OutlinedTextField(
                value = title.value,
                onValueChange = { title.value = it },
                placeholder = {
                    Text(text = stringResource(R.string.insert_title))
                },
                errorColor = MaterialTheme.colors.onError,
                isErrorValue = isInvalid,
                modifier = Modifier.fillMaxWidth()
                    .padding(top = Dimension.defaultMargin)
            )
            OutlinedTextField(value = description.value, onValueChange = {
                description.value = it
            }, placeholder = {
                Text(stringResource(R.string.insert_description))
            },
                modifier = Modifier.fillMaxWidth()
                    .padding(top = Dimension.defaultMargin, bottom = Dimension.defaultMargin)
                    .fillMaxHeight()
            )
        } else {
            note?.let {
                Providers(AmbientContentAlpha provides ContentAlpha.high, content = {
                    Text(
                        text = it.date, style = MaterialTheme.typography.subtitle2,
                        modifier = Modifier.padding(top = Dimension.largeMargin)
                    )
                })
                Divider(
                    Modifier.fillMaxWidth()
                        .padding(end = Dimension.defaultMargin, top = Dimension.smallMargin)
                )
                Text(
                    text = it.description, style = MaterialTheme.typography.body1, modifier =
                    Modifier.padding(
                        top = Dimension.defaultMargin,
                        bottom = Dimension.defaultMargin
                    )
                )
            }

        }
    }
}


@Composable
fun appBar(
    viewModel: NoteViewModel,
    appBarStateDetail: MutableState<StateScreenDetail>,
    note: UiNote?,
    backStack: () -> Unit,
    onSave: () -> Unit
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 0.dp,
        navigationIcon = {
            IconButton(
                onClick = {
                    backStack()
                },
                content = { Icon(Icons.Default.ArrowBack) }
            )
        },
        title = {
            when (appBarStateDetail.value) {
                StateScreenDetail.EDIT -> Text(
                    stringResource(R.string.update_note),
                    style = MaterialTheme.typography.h5
                )
                StateScreenDetail.INSERT -> Text(
                    stringResource(R.string.new_note),
                    style = MaterialTheme.typography.h5
                )
                StateScreenDetail.READ -> note?.title?.let {
                    Text(
                        it,
                        style = MaterialTheme.typography.h5
                    )
                }
            }
        },
        actions = {
            when (appBarStateDetail.value) {
                StateScreenDetail.INSERT -> {
                    IconButton(
                        onClick = { onSave() },
                        content = { Icon(Icons.Default.Save) }
                    )
                }
                StateScreenDetail.READ -> {
                    IconButton(
                        onClick = {
                            appBarStateDetail.value = StateScreenDetail.EDIT
                        },
                        content = { Icon(Icons.Default.Edit) }
                    )
                    IconButton(
                        onClick = {
                            viewModel.delete(note?.uuid)
                            backStack()
                        },
                        content = { Icon(Icons.Default.Delete) }
                    )
                }
                StateScreenDetail.EDIT -> {
                    IconButton(
                        onClick = {
                            appBarStateDetail.value = StateScreenDetail.READ
                        },
                        content = { Icon(Icons.Default.Undo) }
                    )
                    IconButton(
                        onClick = {
                            viewModel.updateNote()
                            backStack()
                        },
                        content = { Icon(Icons.Default.Save) }
                    )
                }
            }
        }
    )
}



