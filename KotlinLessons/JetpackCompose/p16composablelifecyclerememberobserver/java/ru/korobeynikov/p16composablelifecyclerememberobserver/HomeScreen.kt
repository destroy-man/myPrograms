package ru.korobeynikov.p16composablelifecyclerememberobserver

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun HomeScreen() = Column {
    var checked by remember {
        mutableStateOf(false)
    }
    Checkbox(checked = checked, onCheckedChange = { checked = it })
    if (checked) {
        val myObject = remember { MyObject() }
        val a = 1 / 0
    }
}