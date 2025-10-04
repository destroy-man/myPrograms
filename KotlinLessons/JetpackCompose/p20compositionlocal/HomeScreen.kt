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
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp

const val TAG = "myLogs"

val LocalFontStyle = compositionLocalOf { FontStyle.Normal }
val LocalFontStyleStatic = staticCompositionLocalOf { FontStyle.Normal }
val LocalFontStyleWithoutDefault = compositionLocalOf {
    throw IllegalStateException("LocalFontStyle should provide some value")
}

@Composable
fun HomeScreen() {
    //compositionLocalOf without default value
    Column {
        MyTextWithException(text = "Text")
    }
}

@Composable
fun MyTextWithException(text: String) {
    Text(text = text, fontStyle = LocalFontStyleWithoutDefault.current)
}

@Composable
fun HomeScreenDataClassStyleAndMultipleValuesComposition() {
    Column {
        val myTextStyle = MyTextStyle(
            color = Color.Green,
            fontSize = 16.sp
        )
        CompositionLocalProvider(
            LocalFontStyle provides FontStyle.Italic,
            LocalMyTextStyle provides myTextStyle
        ) {
            MyTextWithStyle(text = "Text")
        }
    }
}

@Composable
fun MyTextWithStyle(text: String) {
    val myTextStyle = LocalMyTextStyle.current
    Text(
        text = text,
        color = myTextStyle.color,
        textAlign = myTextStyle.align,
        fontSize = myTextStyle.fontSize,
        fontStyle = LocalFontStyle.current
    )
}

@Composable
fun HomeScreenStaticCompositionLocal() {
    Column {
        val italicState = remember { mutableStateOf(false) }
        MyCheckbox("Italic", italicState)

        Log.d(TAG, "HomeScreen ${italicState.value}")

        val fontStyle = if (italicState.value) FontStyle.Italic else FontStyle.Normal
        CompositionLocalProvider(LocalFontStyle provides fontStyle) {
            MyText(text = "Text")
            Test()
        }
    }
}

@Composable
fun HomeScreenProvidesWithoutPoint() {
    Column {
        val italicState = remember { mutableStateOf(false) }
        MyCheckbox("Italic", italicState)

        val fontStyle = if (italicState.value) FontStyle.Italic else FontStyle.Normal
        CompositionLocalProvider(LocalFontStyle provides fontStyle) {
            MyText(text = "Text 1")
            MyText(text = "Text 2")
            MyText(text = "Text 3")
            MyText(text = "Text 4")
        }
        MyText(text = "Text 5")
    }
}

@Composable
fun HomeScreenPointProvides() {
    Column {
        val italicState = remember { mutableStateOf(false) }
        MyCheckbox("Italic", italicState)

        val fontStyle = if (italicState.value) FontStyle.Italic else FontStyle.Normal
        val localFontStyle = LocalFontStyle.provides(fontStyle)
        CompositionLocalProvider(localFontStyle) {
            MyText(text = "Text 1")
            MyText(text = "Text 2")
            MyText(text = "Text 3")
            MyText(text = "Text 4")
        }
        MyText(text = "Text 5")
    }
}

@Composable
fun MyCheckbox(text: String, checked: MutableState<Boolean>) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = checked.value, onCheckedChange = { checked.value = it })
        Text(text = text)
    }
}

@Composable
fun MyText(text: String) {
    Log.d(TAG, "MyText")
    Text(text = text, fontStyle = LocalFontStyle.current)
}

@Composable
fun Test() {
    Log.d(TAG, "Test")
    Text(text = "Test")
}