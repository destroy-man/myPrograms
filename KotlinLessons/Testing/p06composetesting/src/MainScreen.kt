package ru.korobeynikov.p06composetesting

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun EditTextScreen() {
    var text by remember { mutableStateOf("") }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Text:", modifier = Modifier.padding(end = 10.dp))
        TextField(
            value = text,
            modifier = Modifier
                .weight(1f)
                .testTag(EditTextTags.EDIT_TEXT),
            onValueChange = { newText ->
                text = newText
            }
        )
    }
}

@Composable
fun ListScreen() {
    val data = remember { mutableStateOf((1 until 100).map { it.toString() }) }
    LazyColumn(Modifier.testTag(ListTags.LIST)) {
        items(data.value, { it }) { item ->
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .testTag(ListTags.ITEM),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("item $item", modifier = Modifier.testTag(ListTags.TEXT))
                if (item.toInt() % 5 == 0) {
                    Image(
                        painter = painterResource(R.drawable.ic_launcher_background),
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)
                            .testTag(ListTags.ICON)
                            .clickable {
                                data.value = data.value.filter { it != item }
                            }
                    )
                }
            }
        }
    }
}

@Composable
fun Counter() {
    Box(Modifier.fillMaxSize()) {
        val count = remember { mutableIntStateOf(0) }
        Text(
            text = count.intValue.toString(),
            modifier = Modifier
                .align(Alignment.Center)
                .testTag(CounterTags.TEXT)
        )
        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .testTag(CounterTags.BUTTON),
            onClick = {
                count.intValue++
            }) {
            Text("Add", modifier = Modifier.testTag(CounterTags.BUTTON_TEXT))
        }
    }
}