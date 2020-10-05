package com.example.compose


import androidx.compose.foundation.Box
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.example.compose.ui.Dimension
import com.example.compose.ui.colorsArray
import com.supercaliman.domain.UiNote

@Composable
fun HomeScreen(viewModels: NoteViewModel, onItemClick: (UiNote) -> Unit) {
    val data: List<UiNote> by viewModels.uiLiveData.observeAsState(listOf())
    NotesList(
        items = data,
        onItemClick = onItemClick
    )
}


@Composable
private fun NotesList(
    items: List<UiNote>,
    onItemClick: (UiNote) -> Unit
) {
    Surface(Modifier.fillMaxSize()) {
        LazyColumnFor(items) {
            noteCard(it, onItemClick)
        }
    }
}

@Preview
@Composable
private fun previewCard() {
    noteCard(UiNote(null, "Pippo", "desc", "24 Giugno 2020"), {})
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
                top = Dimension.defaultMargin
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
                        style = MaterialTheme.typography.subtitle2
                    )
                }
            }
        }
    }
}