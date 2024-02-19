package ru.korobeynikov.p20compositionlocal

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

const val TAG = "myLogs"

val LocalMyTextStyle = compositionLocalOf {
    MyTextStyle()
}

@Composable
fun HomeScreen() = Column {
    val myStyleState = remember {
        mutableStateOf(false)
    }
    MyCheckbox(text = "My style", checked = myStyleState)
    Log.d(TAG, "HomeScreen ${myStyleState.value}")
    val myTextStyle = if (myStyleState.value)
        MyTextStyle(color = Color.Green, fontSize = 16.sp)
    else
        MyTextStyle()
    CompositionLocalProvider(LocalMyTextStyle provides myTextStyle) {
        MyText(text = "Text")
        Test()
    }
}

@Composable
fun MyCheckbox(text: String, checked: MutableState<Boolean>) =
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = checked.value, onCheckedChange = {
            checked.value = it
        })
        Text(text = text)
    }

@Composable
fun MyText(text: String) {
    Log.d(TAG, "MyText")
    val myTextStyle = LocalMyTextStyle.current
    Text(
        text = text,
        color = myTextStyle.color,
        textAlign = myTextStyle.align,
        fontSize = myTextStyle.fontSize
    )
}

@Composable
fun Test() {
    Log.d(TAG, "Test")
    Text(text = "Test")
}