package ru.korobeynikov.p20compositionlocal

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class MyTextStyle(
    val color: Color = Color.Unspecified,
    val fontSize: TextUnit = 12.sp,
    val align: TextAlign = TextAlign.Left,
)