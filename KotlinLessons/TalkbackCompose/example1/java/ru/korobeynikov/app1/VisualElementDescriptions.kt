package ru.korobeynikov.app1

import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

@Composable
fun VisualElementDescriptions() {
    val context = LocalContext.current
    IconButton(onClick = {
        Toast.makeText(context, "Переход назад", Toast.LENGTH_SHORT).show()
    }) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(R.string.cd_navigate_up_content_description)
        )
    }
}