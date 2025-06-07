package ru.korobeynikov.app2

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp

@Composable
fun StateDescriptionAndRole() {
    val isEnabled = true
    val enabledDesc = stringResource(R.string.component_state_enabled)
    val disabledDesc = stringResource(R.string.component_state_disabled)
    Box(modifier = Modifier.semantics {
        stateDescription = if (isEnabled) {
            enabledDesc
        } else {
            disabledDesc
        }
        role = Role.Button
    }) {
        Image(
            modifier = Modifier.padding(10.dp),
            imageVector = ImageVector.vectorResource(R.drawable.ic_launcher_background),
            contentDescription = null
        )
    }
}

@Composable
fun ContentDescription() {
    val desc = stringResource(R.string.component_content_description)
    Box(modifier = Modifier.semantics {
        contentDescription = desc
    }) {
        Image(
            modifier = Modifier.padding(10.dp),
            imageVector = ImageVector.vectorResource(R.drawable.ic_launcher_background),
            contentDescription = null
        )
    }
}

@Composable
fun ImageContentDescription() {
    Image(
        imageVector = ImageVector.vectorResource(R.drawable.ic_launcher_background),
        contentDescription = stringResource(R.string.image_content_description)
    )
}