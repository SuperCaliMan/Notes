package com.example.compose.ui

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.compose.R


@Composable
fun renderError(error: Exception) {
    error.message?.let {
        showErrorDialog(it)
    }
}

@Composable
fun progressBar() {
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colors.primary
        )
    }
}


@Composable
fun showErrorDialog(msg: String) {
    val showingDialog = remember { mutableStateOf(true) }
    if (showingDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showingDialog.value = false
            },
            text = {
                Text(text = msg)
            },
            title = {
                Text(text = stringResource(R.string.warning), color = MaterialTheme.colors.error)
            },
            buttons = {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.close),
                        modifier = Modifier.padding(Dimension.defaultMargin).clickable(onClick = {
                            showingDialog.value = false
                        })
                    )
                }
            })
    }

}


fun isDarkTheme(context: Context): Boolean {
    return context.resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
}