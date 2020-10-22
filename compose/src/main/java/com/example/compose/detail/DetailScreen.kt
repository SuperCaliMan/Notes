package com.ex

import androidx.compose.foundation.Icon
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.compose.NoteViewModel
import com.example.compose.R
import com.example.compose.detail.StateScreen
import com.example.compose.ui.Dimension
import com.example.compose.ui.progressBar
import com.example.compose.ui.renderError
import com.supercaliman.domain.UiNote

interface OnSaveListener {
    fun onSave(title: String, description: String)
}

@Composable
fun detailScreen(
    viewModel: NoteViewModel,
    initialState: StateScreen,
    backStack: () -> Unit,
    note: UiNote?
) {
    val state: StateScreen by viewModel.stateScreen.observeAsState(initialState)
    val loading: Boolean by viewModel.loadingStatus.observeAsState(false)
    val error: Exception? by viewModel.errorStatus.observeAsState()
    error?.let {
        renderError(error = it)
    }

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
                when (state) {
                    StateScreen.READ -> bodyDetail(true, note, onSaveObj)
                    StateScreen.INSERT -> bodyDetail(false, null, onSaveObj)
                    StateScreen.EDIT -> bodyDetail(false, note, onSaveObj)
                }
            }
        }
    )
}


@Composable
fun bodyDetail(isReadable: Boolean, note: UiNote?, onSaveListener: OnSaveListener) {
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
                    .padding(top = Dimension.defaultMargin),
                //.preferredHeight(100.dp)
                //.fillMaxHeight(),
                //backgroundColor = MaterialTheme.colors.background,
                //activeColor = MaterialTheme.colors.background,
                keyboardType = KeyboardType.Text,
            )
            OutlinedTextField(value = description.value, onValueChange = {
                description.value = it
            }, placeholder = {
                Text(stringResource(R.string.insert_description))
            },
                modifier = Modifier.fillMaxWidth()
                    .padding(top = Dimension.defaultMargin, bottom = Dimension.defaultMargin)
                    .fillMaxHeight(),
                //backgroundColor = MaterialTheme.colors.background,
                //activeColor = MaterialTheme.colors.background,
                keyboardType = KeyboardType.Text
            )
        } else {
            note?.let {
                ProvideEmphasis(emphasis = EmphasisAmbient.current.medium) {
                    Text(
                        text = it.date, style = MaterialTheme.typography.subtitle2,
                        modifier = Modifier.padding(top = Dimension.largeMargin)
                    )
                }
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
    stateUi: StateScreen,
    note: UiNote?,
    backStack: () -> Unit,
    onSave: () -> Unit
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 6.dp,
        //modifier = Modifier.clip(shape = RoundedCornerShape(bottomRightPercent = 50,bottomLeftPercent = 50)), //clip edge app bar
        navigationIcon = {
            Icon(Icons.Default.ArrowBack, modifier =
            Modifier
                .padding(start = Dimension.defaultMargin)
                .clickable {
                    backStack()
                })
        },
        title = {
            when (stateUi) {
                StateScreen.EDIT -> Text("", style = MaterialTheme.typography.h5)
                StateScreen.INSERT -> Text(
                    stringResource(R.string.new_note),
                    style = MaterialTheme.typography.h5
                )
                StateScreen.READ -> note?.title?.let {
                    Text(
                        it,
                        style = MaterialTheme.typography.h5
                    )
                }
            }
        },
        actions = {
            when (stateUi) {
                StateScreen.INSERT -> {
                    Icon(Icons.Default.Save, modifier = Modifier
                        .padding(end = Dimension.defaultMargin)
                        .clickable {
                            onSave()
                        })
                }
                StateScreen.READ -> {
                    Icon(Icons.Default.Edit, modifier = Modifier
                        .padding(end = Dimension.defaultMargin)
                        .clickable {
                            viewModel.setEditMode()
                        })
                    Icon(Icons.Default.Delete, modifier = Modifier
                        .padding(end = Dimension.defaultMargin)
                        .clickable {
                            viewModel.delete(note?.uuid)
                            backStack()
                        })
                }
                StateScreen.EDIT -> {
                    Icon(Icons.Default.Undo, modifier = Modifier
                        .padding(end = Dimension.defaultMargin)
                        .clickable {
                            viewModel.prevMode()
                        })
                    Icon(Icons.Default.Save, modifier = Modifier
                        .padding(end = Dimension.defaultMargin)
                        .clickable {
                            viewModel.updateNote()
                            backStack()
                        })
                }
            }
        }
    )
}



