package ru.korobeynikov.p11listcallsitekey

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

const val TAG = "myLogs"

@Composable
fun HomeScreen() {
    Log.d(TAG, "HomeScreen")
    val list = remember {
        List(20) { "Item ${it + 1}" }.toMutableStateList()
    }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.border(width = 2.dp, color = Color.Green)
    ) {
        items(list.size) { index ->
            SomeItem(text = list[index])
        }
    }
}

@Composable
fun HomeScreenScrollColumn() {
    Log.d(TAG, "HomeScreen")
    val list = remember {
        List(20) { "Item ${it + 1}" }.toMutableStateList()
    }
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.verticalScroll(scrollState)) {
        list.forEach { value ->
            key(value) {
                SomeItem(value)
            }
        }
    }
}

@Composable
fun HomeScreenInsert() {
    Log.d(TAG, "HomeScreen")
    val list = remember { mutableStateListOf("Item 1", "Item 2", "Item 3") }
    Column {
        TextButton(onClick = {
            Log.d(TAG, "--- insert ---")
            list.add(0, "Item ${list.size + 1}")
        }) {
            Text(text = "Insert")
        }
        list.forEach { value ->
            key(value) {
                SomeItem(value)
            }
        }
    }
}

@Composable
fun SomeItem(text: String) {
    Log.d(TAG, "SomeItem $text")
    Text(
        text = text,
        fontSize = 20.sp,
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.Black)
            .padding(16.dp)
    )
}