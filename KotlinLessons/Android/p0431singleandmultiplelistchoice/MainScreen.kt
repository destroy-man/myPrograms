package ru.korobeynikov.p0431singleandmultiplelistchoice

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen(isSingleChoiceMode: Boolean) {
    val names = stringArrayResource(R.array.names)
    val countSelectedItems = if (isSingleChoiceMode) 1 else names.size
    val namesChecked = remember { mutableStateListOf<String>() }
    Column {
        Button(onClick = {
            Log.d("myLogs", "checked:\n${namesChecked.joinToString("\n")}")
        }) {
            Text("Get checked items")
        }
        LazyColumn {
            items(names) { name ->
                ListElement(name, selected = namesChecked.contains(name)) {
                    if (namesChecked.contains(name)) {
                        namesChecked.remove(name)
                    } else if (namesChecked.size == countSelectedItems) {
                        namesChecked.removeAt(0)
                        namesChecked.add(name)
                    } else {
                        namesChecked.add(name)
                    }
                }
            }
        }
    }
}

@Composable
fun ListElement(value: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Text(
            value,
            fontSize = 24.sp,
            modifier = Modifier.padding(start = 5.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        RadioButton(selected, onClick = onClick)
    }
    HorizontalDivider(modifier = Modifier.fillMaxWidth())
}