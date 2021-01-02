package com.example.compose.home

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.example.compose.R
import com.example.compose.domain.UiNote
import com.example.compose.ui.Dimension
import com.example.compose.ui.colorsArray
import com.example.compose.ui.isDarkTheme

/**
 * Display a lottie animation use android view because lottie doesn't support compose yet
 */
@Composable
fun lottieAnimation(modifier: Modifier) {
    AndroidView(
        viewBlock = {
            LottieAnimationView(it).apply {
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
fun appBarHome() {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 0.dp,
        title = {
            Text(
                stringResource(R.string.title_activity_main_compose),
                style = MaterialTheme.typography.h5
            )
        },
        actions = {
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
    )
}

@Composable
fun noteList(
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
            Providers(AmbientContentAlpha provides ContentAlpha.high, content = {
                Text(
                    stringResource(R.string.no_data),
                    modifier = Modifier.padding(bottom = Dimension.largeMargin)
                        .align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.body1
                )
            })
        }

    } else {
        Surface(Modifier.fillMaxSize()) {
            LazyColumn(
                contentPadding = PaddingValues(
                    top = Dimension.defaultMargin,
                    bottom = Dimension.largeMargin * 2
                )
            ) {
                items(items) {
                    noteCard(it, onItemClick)
                }
            }
        }
    }
}


@Composable
fun noteCard(note: UiNote, click: (UiNote) -> Unit) {
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
                modifier = Modifier
                    .preferredSize(15.dp, 100.dp)
                    .background(colorsArray[note.title.length % colorsArray.size]),
            )
            Column(
                modifier = Modifier.padding(
                    start = Dimension.defaultMargin,
                    top = Dimension.defaultMargin
                ).align(Alignment.CenterVertically)

            ) {
                Text(note.title, style = MaterialTheme.typography.h6)
                Providers(AmbientContentAlpha provides ContentAlpha.high, content = {
                    Text(
                        note.date,
                        modifier = Modifier.padding(
                            top = Dimension.defaultMargin,
                            bottom = Dimension.defaultMargin
                        ),
                        style = MaterialTheme.typography.subtitle1
                    )
                })
            }
        }
    }
}