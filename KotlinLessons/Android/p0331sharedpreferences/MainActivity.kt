package ru.korobeynikov.p0331sharedpreferences

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.content.edit

class MainActivity : ComponentActivity() {

    val spText = "savedText"
    var text by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                MainScreen(
                    text = text,
                    onChangeText = { value ->
                        text = value
                    },
                    onSaveToSharedPreferences = ::saveText,
                    onLoadFromSharedPreferences = ::loadText
                )
            }
        }
        loadText()
    }

    override fun onStop() {
        super.onStop()
        saveText()
    }

    private fun saveText() {
        val sPref = getPreferences(MODE_PRIVATE)
        sPref.edit {
            putString(spText, text)
        }
        Toast.makeText(
            this,
            "Text saved",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun loadText() {
        val sPref = getPreferences(MODE_PRIVATE)
        val savedText = sPref.getString(spText, "") ?: ""
        text = savedText
        Toast.makeText(
            this,
            "Text loaded",
            Toast.LENGTH_SHORT
        ).show()
    }
}