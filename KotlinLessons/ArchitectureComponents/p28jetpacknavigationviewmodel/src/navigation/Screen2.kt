package ru.korobeynikov.p28jetpacknavigationviewmodel.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.korobeynikov.p28jetpacknavigationviewmodel.viewmodel.ScreenViewModel
import ru.korobeynikov.p28jetpacknavigationviewmodel.viewmodel.ScreenViewModelFactory

@Composable
fun Screen2(
    backStack: SnapshotStateList<Screen>,
    //viewModel: ScreenViewModel = viewModel(factory = ScreenViewModelFactory())
    viewModel: ScreenViewModel = viewModel()
) {
    Column {
        Text("Screen 2", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        Text(
            "ViewModel = ${viewModel.hashCode()}",
            modifier = Modifier.padding(start = 10.dp, top = 20.dp)
        )
        Row(modifier = Modifier.padding(top = 20.dp)) {
            Button(modifier = Modifier.padding(horizontal = 10.dp), onClick = {
                backStack.removeLastOrNull()
            }) {
                Text("Back")
            }
            Button(onClick = {
                backStack.add(Screen.Screen3)
            }) {
                Text("Next")
            }
        }
        BackHandler {
            backStack.removeLastOrNull()
        }
    }
}