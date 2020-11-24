package com.example.compose.chat


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.example.compose.R


@Composable
fun chatScreen() {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Image(
            asset = imageResource(R.drawable.rick),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .preferredWidth(200.dp)
                .preferredHeight(200.dp)
                .align(Alignment.CenterHorizontally)
        )
    }

}