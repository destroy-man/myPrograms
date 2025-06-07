package ru.korobeynikov.app2

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomActions() {
    val state = rememberSwipeToDismissBoxState()
    val scope = rememberCoroutineScope()
    val deleteString = stringResource(R.string.delete)
    SwipeToDismissBox(state = state, backgroundContent = {}) {
        Column(
            modifier = Modifier
                .semantics(mergeDescendants = true) {
                    customActions = listOf(
                        CustomAccessibilityAction(deleteString) {
                            scope.launch {
                                state.dismiss(SwipeToDismissBoxValue.StartToEnd)
                            }
                            true
                        }
                    )
                }
                .fillMaxWidth()
                .padding(16.dp)

        ) {
            Text(
                modifier = Modifier.padding(bottom = 4.dp),
                text = "Хабр",
                fontWeight = FontWeight.Bold
            )
            Text(text = "Самое интересное по вашим хабам")
        }
    }
}