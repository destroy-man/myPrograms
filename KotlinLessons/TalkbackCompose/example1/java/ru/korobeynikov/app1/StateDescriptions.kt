package ru.korobeynikov.app1

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StateDescriptions(selected: Boolean, onCheckedChange: (Boolean) -> Unit) {
    val stateNotSubscribed = stringResource(R.string.state_not_subscribed)
    val stateSubscribed = stringResource(R.string.state_subscribed)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .semantics {
                stateDescription = if (selected) {
                    stateSubscribed
                } else {
                    stateNotSubscribed
                }
            }
            .toggleable(
                value = selected,
                onValueChange = onCheckedChange,
                role = Role.Checkbox
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = null
        )
        Text(
            text = "Джетпак компоуз",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .weight(1f)
        )
        Checkbox(
            checked = selected,
            onCheckedChange = null
        )
    }
}