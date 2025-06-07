package ru.korobeynikov.app1

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp

@Composable
fun ClickLabels() {
    PostCardPopular()
}

@Composable
fun PostCardHistory() {
    val context = LocalContext.current
    Row(Modifier.clickable(onClickLabel = stringResource(R.string.action_read_article_content_description)) {
        Toast.makeText(context, "Нажатие на ряд", Toast.LENGTH_SHORT).show()
    }) {
        Image(painter = painterResource(R.drawable.ic_launcher_background), contentDescription = "")
        Column {
            Text(text = "Немного о путях к модулям Андроид", fontWeight = Bold)
            Text("Пьетро Маги - 1 минута на чтение")
        }
        IconButton(onClick = {
            Toast.makeText(context, "Нажатие на иконку закрытия", Toast.LENGTH_SHORT).show()
        }) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(R.string.close_icon_content_description)
            )
        }
    }
}

@Composable
fun PostCardPopular(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val readArticleLabel = stringResource(R.string.action_read_article_content_description)
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .size(280.dp, 240.dp)
            .semantics {
                onClick(label = readArticleLabel, action = null)
            },
        onClick = {
            Toast.makeText(context, "Нажатие на карточку", Toast.LENGTH_SHORT).show()
        }
    ) {
        Column {
            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = ""
            )
            Text(text = "Локальные изменение и антипаттерн Андроид Вью Модель", fontWeight = Bold)
            Text("Хосе Альсерека")
            Text("1 минута на чтение")
        }
    }
}