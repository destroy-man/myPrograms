package ru.korobeynikov.app2

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.CollectionInfo
import androidx.compose.ui.semantics.CollectionItemInfo
import androidx.compose.ui.semantics.collectionInfo
import androidx.compose.ui.semantics.collectionItemInfo
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun LazyLists() {
    val versions = listOf("1", "2", "3")
    LazyColumn(modifier = Modifier.semantics {
        collectionInfo = CollectionInfo(versions.size, 1)
    }) {
        itemsIndexed(
            items = versions,
            key = { _, version ->
                version.hashCode()
            }
        ) { index, version ->
            Text(
                modifier = Modifier
                    .semantics {
                        collectionItemInfo = CollectionItemInfo(
                            rowIndex = index,
                            rowSpan = 1,
                            columnIndex = 0,
                            columnSpan = 1
                        )
                    }
                    .fillMaxWidth()
                    .padding(16.dp),
                text = version
            )
        }
    }
}

@Composable
fun Lists() {
    val versions = listOf("1", "2", "3")
    Column(modifier = Modifier.semantics {
        collectionInfo = CollectionInfo(versions.size, 1)
    }) {
        versions.forEachIndexed { index, version ->
            Text(
                modifier = Modifier
                    .semantics {
                        collectionItemInfo = CollectionItemInfo(
                            rowIndex = index,
                            rowSpan = 1,
                            columnIndex = 1,
                            columnSpan = 1
                        )
                    }
                    .fillMaxWidth()
                    .padding(16.dp),
                text = version
            )
        }
    }
}