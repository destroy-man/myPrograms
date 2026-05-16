package ru.korobeynikov.p0581timepickerdialog

import androidx.compose.foundation.clickable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDialog
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val currentTime = Calendar.getInstance()
    var myHour by remember { mutableIntStateOf(currentTime.get(Calendar.HOUR_OF_DAY)) }
    var myMinute by remember { mutableIntStateOf(currentTime.get(Calendar.MINUTE)) }
    var isAlertDialogShow by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState(
        initialHour = myHour,
        initialMinute = myMinute,
        is24Hour = true
    )
    Text(
        stringResource(R.string.time_text, myHour, myMinute),
        fontSize = 22.sp,
        modifier = Modifier.clickable {
            isAlertDialogShow = true
        }
    )
    if (isAlertDialogShow) {
        TimePickerDialog(
            onDismissRequest = {
                isAlertDialogShow = false
            },
            title = {},
            confirmButton = {
                TextButton(onClick = {
                    isAlertDialogShow = false
                    myHour = timePickerState.hour
                    myMinute = timePickerState.minute
                }) {
                    Text(stringResource(R.string.confirm_button_text))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    isAlertDialogShow = false
                }) {
                    Text(stringResource(R.string.dismiss_button_text))
                }
            }
        ) {
            TimePicker(timePickerState)
        }
    }
}