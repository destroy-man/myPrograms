package ru.korobeynikov.p0351jsondatastore

import android.content.Context
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
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<JsonText> by dataStore(
    fileName = "text.json",
    serializer = JsonTextSerializer
)

class MainActivity : ComponentActivity() {

    lateinit var jsonFlow: Flow<String>

    var text by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        jsonFlow = this.dataStore.data.map { jsonText ->
            jsonText.text
        }

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
    }

    private suspend fun saveText() {
        this.dataStore.updateData { jsonText ->
            jsonText.copy(text = text)
        }.also {
            Toast.makeText(
                this,
                "Text saved",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private suspend fun loadText() {
        jsonFlow.collect { value ->
            text = value
            Toast.makeText(
                this,
                "Text loaded",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}