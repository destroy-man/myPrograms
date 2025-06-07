package ru.korobeynikov.app1

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun TouchTargetSize() {
    val context = LocalContext.current
    IconButton(onClick = {
        Toast.makeText(context, "Нажата иконка с крестиком", Toast.LENGTH_SHORT).show()
    }) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = stringResource(R.string.close_icon_content_description)
        )
    }
}

@Composable
fun IconWithPadding() {
    val context = LocalContext.current
    Icon(
        imageVector = Icons.Default.Close,
        contentDescription = stringResource(R.string.close_icon_content_description),
        modifier = Modifier
            .clickable {
                Toast
                    .makeText(context, "Нажата иконка с крестиком", Toast.LENGTH_SHORT)
                    .show()
            }
            .padding(12.dp)
            .size(24.dp)
    )
}