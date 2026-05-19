package ru.korobeynikov.p0601alertdialog

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen() {
    val activity = LocalActivity.current
    var isAlertDialogShow by remember { mutableStateOf(false) }
    Button(onClick = {
        isAlertDialogShow = true
    }) {
        Text(stringResource(R.string.exit))
    }
    BackHandler {
        isAlertDialogShow = true
    }
    if (isAlertDialogShow) {
        AlertDialog(
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(android.R.drawable.ic_dialog_info),
                        contentDescription = null,
                        modifier = Modifier.padding(end = 5.dp)
                    )
                    Text(stringResource(R.string.exit))
                }
            },
            text = {
                Text(stringResource(R.string.save_data))
            },
            onDismissRequest = {
                isAlertDialogShow = false
            },
            confirmButton = {
                TextButton(onClick = {
                    Toast.makeText(
                        activity,
                        activity?.getString(R.string.saved),
                        Toast.LENGTH_SHORT
                    ).show()
                    activity?.finish()
                }) {
                    Text(stringResource(R.string.yes))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    activity?.finish()
                }) {
                    Text(stringResource(R.string.no))
                }
            }
        )
    }
}