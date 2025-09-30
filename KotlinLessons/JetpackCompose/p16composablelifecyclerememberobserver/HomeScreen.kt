package ru.korobeynikov.p16composablelifecyclerememberobserver

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun HomeScreen() {
    //onAbandoned
    Column {
        var checked by remember { mutableStateOf(false) }
        Checkbox(checked = checked, onCheckedChange = { checked = it })
        if (checked) {
            val myObject = remember { MyObject() }
            val a = 1 / 0
        }
    }
}

@Composable
fun HomeScreenMyObject() {
    Column {
        var checked by remember { mutableStateOf(false) }
        Checkbox(checked = checked, onCheckedChange = { checked = it })
        if (checked) {
            val myObject = remember { MyObject() }
        }
    }
}

@Composable
fun HomeScreenCheckedRemember() {
    Column {
        var checked by remember { mutableStateOf(true) }
        Checkbox(checked = checked, onCheckedChange = { checked = it })
        val homeTime = remember { currentTime() }
        if (checked) {
            val time = remember { currentTime() }
            Text(" homeTime = $homeTime \n time = $time")
        }
    }
}

private fun currentTime(): String {
    return SimpleDateFormat("HH:mm:ss", Locale.current.platformLocale).format(Date())
}

@Composable
fun HomeScreenWithClickCounter() {
    Column {
        var checked by remember { mutableStateOf(false) }
        Checkbox(checked = checked, onCheckedChange = { checked = it })
        if (checked) {
            ClickCounter()
        }
    }
}

@Composable
fun ClickCounter() {
    var count by remember { mutableIntStateOf(0) }
    Text(text = "Count $count", modifier = Modifier.clickable { count++ })
}