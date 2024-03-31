package ru.korobeynikov.uiautomator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val stateTextField = remember {
                mutableStateOf("")
            }
            Column(modifier = Modifier.semantics {
                testTagsAsResourceId = true
            }) {
                val navHostController = rememberNavController()
                NavHost(navController = navHostController, startDestination = "old") {
                    composable("old") {
                        Column {
                            Button(onClick = {
                                stateTextField.value = "Hello, UI Automator!"
                            }) {
                                Text(text = "Click me")
                            }
                            OutlinedTextField(
                                modifier = Modifier.testTag("editText"),
                                value = stateTextField.value,
                                onValueChange = {
                                    stateTextField.value = it
                                })
                            Button(onClick = {
                                navHostController.navigate("new")
                            }) {
                                Text(text = "Next")
                            }
                        }
                    }
                    composable("new") {
                        Text(text = "New Screen")
                    }
                }
            }
        }
    }
}