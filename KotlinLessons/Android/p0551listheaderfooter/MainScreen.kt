package ru.korobeynikov.p0551listheaderfooter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen() {
    val data = listOf("one", "two", "three", "four", "five")
    Column {
        LazyColumn {
            item {
                Header("Header element")
            }
            items(data) { text ->
                ListElement(text)
            }
            item {
                Footer("Footer element")
            }
        }
    }
}

@Composable
fun ListElement(text: String) {
    Column {
        Text(text, fontSize = 20.sp, modifier = Modifier.padding(5.dp))
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun Header(text: String) {
    Column {
        Text(stringResource(R.string.header_title))
        Text(text, fontSize = 20.sp)
    }
}

@Composable
fun Footer(text: String) {
    Column {
        Text(stringResource(R.string.footer_title))
        Text(text, fontSize = 20.sp)
    }
}