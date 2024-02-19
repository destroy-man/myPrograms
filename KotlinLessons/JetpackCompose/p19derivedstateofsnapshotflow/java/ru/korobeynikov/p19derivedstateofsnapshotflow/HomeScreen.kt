package ru.korobeynikov.p19derivedstateofsnapshotflow

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen() = Column {
    var count by remember {
        mutableIntStateOf(0)
    }
    Text(text = "count = $count", modifier = Modifier.clickable { count++ })
    val derivedState = remember {
        derivedStateOf {
            count * 10
        }
    }
    val snapshotState = remember {
        snapshotFlow {
            count * 10
        }
    }.collectAsState(initial = 0)
}