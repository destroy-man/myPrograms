package ru.korobeynikov.p21materialdesigntheme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun HomeScreen() {
    Column {
        Text(text = "Some text")
        val medium = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.74f)
        CompositionLocalProvider(LocalContentColor provides medium) {
            Text(text = "Some text")
        }
        val disabled = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        CompositionLocalProvider(LocalContentColor provides disabled) {
            Text(text = "Some text")
        }
    }
}

@Composable
fun HomeScreenSurface() {
    Column {
        Text(
            text = "Some text",
            modifier = Modifier.background(MaterialTheme.colorScheme.error)
        )
        Surface(color = MaterialTheme.colorScheme.error) {
            Text(text = "Some text")
        }
    }
}

@Composable
fun HomeScreenCustomShapes() {
    TextField(value = "Some text", onValueChange = {})
}

@Composable
fun HomeScreenCustomLetterSpacing() {
    Button(onClick = {}) {
        val textStyle = LocalTextStyle.current
        Text(text = "Some text (${textStyle.fontSize}, ${textStyle.letterSpacing})")
    }
}

@Composable
fun HomeScreenButtonAndText() {
    Column {
        Button(onClick = {}) {
            Text(text = "Some text")
        }
        Text(text = "Some text")
    }
}

@Composable
fun HomeScreenButton() {
    Button(onClick = {}) {
        Text(text = "Some text")
    }
}

@Composable
fun HomeScreenCustomAndDefault() {
    Column {
        MaterialTheme(colorScheme = lightColorScheme(primary = Color.Green)) {
            Button(onClick = {}) {
                Text(text = "Some text")
            }
        }
        Button(onClick = {}) {
            Text(text = "Some text")
        }
    }
}

@Composable
fun HomeScreenCustomColorButton() {
    MaterialTheme(colorScheme = lightColorScheme(primary = Color.Green)) {
        Button(onClick = {}) {
            Text(text = "Some text")
        }
    }
}