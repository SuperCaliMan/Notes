package com.example.compose.home


import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.RowScope.align
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.airbnb.lottie.LottieDrawable
import com.example.compose.NoteViewModel
import com.example.compose.R
import com.example.compose.ui.*
import com.example.compose.ui.Dimension
import com.supercaliman.domain.UiNote

@Composable
fun homeScreen(
    viewModel: NoteViewModel,
    newFeature: Boolean,
    onItemClick: (UiNote) -> Unit,
    fabAction: () -> Unit
) {
    val data: List<UiNote> by viewModel.uiLiveData.observeAsState(listOf())
    val errorData: Exception? by viewModel.errorStatus.observeAsState()
    val loading: Boolean by viewModel.loadingStatus.observeAsState(true)

    errorData?.let {
        renderError(it)
    }


    Scaffold(
        topBar = { appBarHome(newFeature) },
        floatingActionButton = {
            if (!loading) {
                ExtendedFloatingActionButton(
                    onClick = {
                        fabAction()
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
                    onItemClick = onItemClick
                )
            }
        }
    )


}


@Composable
private fun noteList(
    items: List<UiNote>,
    onItemClick: (UiNote) -> Unit
) {
    if (items.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(Color.White)
        ) {
            lottieAnimation(
                modifier = Modifier.fillMaxWidth()
            )
            ProvideEmphasis(EmphasisAmbient.current.medium) {
                Text(
                    stringResource(R.string.no_data),
                    modifier = Modifier.padding(bottom = Dimension.largeMargin)
                        .align(Alignment.CenterVertically),
                    style = MaterialTheme.typography.body1
                )
            }
        }

    } else {
        Surface(Modifier.fillMaxSize()) {
            LazyColumnFor(
                items,
                contentPadding = PaddingValues(top = Dimension.defaultMargin)
            ) {
                noteCard(it, onItemClick)
            }
        }
    }
}

/**
 * Display a lottie animation use android view because lottie doesn't support compose yet
 */
@Composable
private fun lottieAnimation(modifier: Modifier) {
    AndroidView(
        viewBlock = {
            com.airbnb.lottie.LottieAnimationView(it).apply {
                setAnimation(R.raw.creativity)
                repeatCount = LottieDrawable.INFINITE
                scale = 0.9F
                playAnimation()
            }
        },
        modifier = modifier.background(Color.White),
    )
}

@Composable
private fun appBarHome(newFeature: Boolean) {
    val state = remember { mutableStateOf(false) }

    if (newFeature && state.value) {
        showMessageDialog("Yep", "it's a new Feature 🎉")
    }

    TopAppBar(
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 0.dp,
        navigationIcon = {
            if (newFeature) {
                IconButton(
                    onClick = {
                        state.value = true
                    },
                    icon = { Icon(Icons.Default.Menu) }
                )
            }
        },
        title = {
            Text(
                stringResource(R.string.title_activity_main_compose),
                style = MaterialTheme.typography.h5
            )
        },
        actions = {
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
    )
}


@Composable
private fun noteCard(note: UiNote, click: (UiNote) -> Unit) {
    Card(
        backgroundColor = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier
            .padding(
                start = Dimension.defaultMargin,
                end = Dimension.defaultMargin,
                bottom = Dimension.defaultMargin
            ).fillMaxWidth()
            .clickable(onClick = { click(note) }),

        ) {
        Row {
            Box(
                backgroundColor = colorsArray[note.title.length % colorsArray.size],
                modifier = Modifier.preferredSize(15.dp, 100.dp),
            )
            Column(
                modifier = Modifier.padding(
                    start = Dimension.defaultMargin,
                    top = Dimension.defaultMargin
                ).align(Alignment.CenterVertically)

            ) {
                Text(note.title, style = MaterialTheme.typography.h6)
                ProvideEmphasis(EmphasisAmbient.current.medium) {
                    Text(
                        note.date,
                        modifier = Modifier.padding(
                            top = Dimension.defaultMargin,
                            bottom = Dimension.defaultMargin
                        ),
                        style = MaterialTheme.typography.subtitle1
                    )
                }
            }
        }
    }
}