package com.supercaliman.home.ui

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.supercaliman.home.R


@Composable
fun RenderError(error: Exception) {
    error.message?.let {
        ShowErrorDialog(it)
    }
}

@Composable
fun ProgressBar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colors.primary,
            strokeWidth = 3.dp
        )
    }
}


@Composable
fun ShowErrorDialog(msg: String) {
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
                        modifier = Modifier
                            .padding(Dimension.defaultMargin)
                            .clickable(onClick = {
                                showingDialog.value = false
                            })
                    )
                }
            })
    }
}

@Composable
fun ShowMessageDaialog(title: String, msg: String, onClose: () -> Unit) {
    val showingDialog = remember { mutableStateOf(true) }
    if (showingDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showingDialog.value = false
            },
            text = {
                Text(text = msg, style = MaterialTheme.typography.subtitle1)
            },
            title = {
                Text(text = title, style = MaterialTheme.typography.h5)
            },
            buttons = {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.close),
                        modifier = Modifier
                            .padding(Dimension.defaultMargin)
                            .clickable(onClick = {
                                showingDialog.value = false
                                onClose()
                            })
                    )
                }
            })
    }
}

@Composable
fun AppDrawer(
    onLogout: () -> Unit = {},
    navController: NavController,
    closeDrawer: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(Modifier.height(24.dp))
        Divider(color = MaterialTheme.colors.onSurface.copy(alpha = .2f))
        TextButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onLogout()
                //closeDrawer()
            }
        ) {
            Text(stringResource(R.string.drawer_logout))
        }

    }
}


fun isDarkTheme(context: Context): Boolean {
    return context.resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
}