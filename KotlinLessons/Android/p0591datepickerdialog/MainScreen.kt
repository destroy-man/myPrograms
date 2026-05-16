package ru.korobeynikov.p0591datepickerdialog

import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.getSelectedDate
import androidx.compose.material3.rememberDatePickerState
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
    val currentDate = Calendar.getInstance()
    var myDay by remember { mutableIntStateOf(currentDate.get(Calendar.DAY_OF_MONTH)) }
    var myMonth by remember { mutableIntStateOf(currentDate.get(Calendar.MONTH)) }
    var myYear by remember { mutableIntStateOf(currentDate.get(Calendar.YEAR)) }
    var isAlertDialogShow by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(currentDate.time.time)
    Text(
        stringResource(R.string.date_text, myDay, myMonth, myYear),
        fontSize = 22.sp,
        modifier = Modifier.clickable {
            isAlertDialogShow = true
        }
    )
    if (isAlertDialogShow) {
        DatePickerDialog(
            onDismissRequest = {
                isAlertDialogShow = false
            },
            confirmButton = {
                TextButton(onClick = {
                    isAlertDialogShow = false
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        datePickerState.getSelectedDate()?.let { date ->
                            myDay = date.dayOfMonth
                            myMonth = date.month.value
                            myYear = date.year
                        }
                    } else {
                        datePickerState.selectedDateMillis?.let { date ->
                            val newDate = Calendar.getInstance()
                            newDate.time.time = date
                            myDay = newDate.get(Calendar.DAY_OF_MONTH)
                            myMonth = newDate.get(Calendar.MONTH)
                            myYear = newDate.get(Calendar.YEAR)
                        }
                    }
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
            },
            content = {
                DatePicker(datePickerState)
            }
        )
    }
}