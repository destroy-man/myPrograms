package ru.korobeynikov.p0671progressdialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

var job: Job? = null

@Composable
fun MainScreen() {
    //Горизонтальный индикатор прогресса
    var currentProgress by remember { mutableFloatStateOf(0f) }
    var loading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    Column {
        Button(enabled = !loading, onClick = {
            loading = true
            job = scope.launch {
                loadProgress { progress ->
                    currentProgress = progress
                }
                loading = false
            }
        }) {
            Text(stringResource(R.string.horiz))
        }
        if (loading) {
            AlertDialog(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(android.R.drawable.ic_dialog_info),
                            contentDescription = null,
                            modifier = Modifier.padding(end = 5.dp)
                        )
                        Text(stringResource(R.string.title_text))
                    }
                },
                text = {
                    Column {
                        val percent = "%.1f".format(currentProgress * 100)
                        Text(stringResource(R.string.percent_text, percent))
                        LinearProgressIndicator(progress = {
                            currentProgress
                        })
                    }
                },
                onDismissRequest = {
                    loading = false
                    job?.cancel()
                },
                confirmButton = {
                    TextButton(onClick = {
                        loading = false
                        job?.cancel()
                    }) {
                        Text(stringResource(R.string.confirm_button_text))
                    }
                }
            )
        }
    }
}

suspend fun loadProgress(updateProgress: (Float) -> Unit) {
    for (i in 1..100) {
        updateProgress(i.toFloat() / 100)
        delay(100)
    }
}

@Composable
fun CircularProgressScreen() {
    var loading by remember { mutableStateOf(false) }
    Column {
        Button(enabled = !loading, onClick = {
            loading = true
        }) {
            Text(stringResource(R.string.circle))
        }
        if (loading) {
            AlertDialog(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(android.R.drawable.ic_dialog_info),
                            contentDescription = null,
                            modifier = Modifier.padding(end = 5.dp)
                        )
                        Text(stringResource(R.string.title_text))
                    }
                },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CircularProgressIndicator()
                        Text(
                            stringResource(R.string.alert_text),
                            modifier = Modifier.padding(start = 5.dp)
                        )
                    }
                },
                onDismissRequest = {
                    loading = false
                },
                confirmButton = {
                    TextButton(onClick = {
                        loading = false
                    }) {
                        Text(stringResource(R.string.confirm_button_text))
                    }
                }
            )
        }
    }
}