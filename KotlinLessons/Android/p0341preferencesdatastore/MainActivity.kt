package ru.korobeynikov.p0341preferencesdatastore

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
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences")

class MainActivity : ComponentActivity() {

    val prefDataStore = stringPreferencesKey("pref")
    lateinit var prefFlow: Flow<String>

    var text by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefFlow = this.dataStore.data.map { preferences ->
            preferences[prefDataStore] ?: ""
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
        this.dataStore.updateData { preferences ->
            preferences.toMutablePreferences().also { mutPreferences ->
                mutPreferences[prefDataStore] = text
                Toast.makeText(
                    this,
                    "Text saved",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private suspend fun loadText() {
        prefFlow.collect { value ->
            text = value
            Toast.makeText(
                this,
                "Text loaded",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}