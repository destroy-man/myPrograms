package ru.korobeynikov.p0361protodatastore

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
import com.example.datastore.snippets.proto.ProtoText
import com.example.datastore.snippets.proto.copy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<ProtoText> by dataStore(
    fileName = "protoText.pb",
    serializer = ProtoTextSerializer
)

class MainActivity : ComponentActivity() {

    lateinit var protoTextFlow: Flow<String>

    var textValue by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        protoTextFlow = this.dataStore.data.map { protoText ->
            protoText.text
        }

        enableEdgeToEdge()
        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                MainScreen(
                    text = textValue,
                    onChangeText = { value ->
                        textValue = value
                    },
                    onSaveToSharedPreferences = ::saveText,
                    onLoadFromSharedPreferences = ::loadText
                )
            }
        }
    }

    private suspend fun saveText() {
        this.dataStore.updateData { protoText ->
            protoText.copy {
                text = textValue
            }.also {
                Toast.makeText(
                    this,
                    "Text saved",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private suspend fun loadText() {
        protoTextFlow.collect { value ->
            textValue = value
            Toast.makeText(
                this,
                "Text loaded",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}