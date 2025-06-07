package ru.korobeynikov.app1

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight.Companion.Bold

@Composable
fun CustomActions() {
    val context = LocalContext.current
    val iconCloseContentDescription = stringResource(R.string.close_icon_content_description)
    Row(Modifier
        .clickable(
            onClickLabel = stringResource(R.string.action_read_article_content_description)
        ) {
            Toast
                .makeText(context, "Нажатие на ряд", Toast.LENGTH_SHORT)
                .show()
        }
        .semantics {
            customActions =
                listOf(CustomAccessibilityAction(label = iconCloseContentDescription, action = {
                    Toast
                        .makeText(context, "Нажатие на иконку закрытия", Toast.LENGTH_SHORT)
                        .show()
                    true
                }))
        }) {
        Image(painter = painterResource(R.drawable.ic_launcher_background), contentDescription = "")
        Column(Modifier.weight(1f)) {
            Text(text = "Немного о путях к модулям Андроид", fontWeight = Bold)
            Text("Пьетро Маги - 1 минута на чтение")
        }
        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurfaceVariant) {
            IconButton(modifier = Modifier.clearAndSetSemantics { }, onClick = {
                Toast.makeText(context, "Нажатие на иконку закрытия", Toast.LENGTH_SHORT).show()
            }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = iconCloseContentDescription
                )
            }
        }
    }
}