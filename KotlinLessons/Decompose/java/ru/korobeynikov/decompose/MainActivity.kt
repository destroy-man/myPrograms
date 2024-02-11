package ru.korobeynikov.decompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.defaultComponentContext
import kotlinx.coroutines.Dispatchers

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootComponent = RealSignInComponent(defaultComponentContext())
        setContent {
            SignInUI(component = rootComponent)
        }
    }

    @Composable
    fun SignInUI(component: SignInComponent) {
        val login by component.login.collectAsState(Dispatchers.Main.immediate)
        val password by component.password.collectAsState(Dispatchers.Main.immediate)
        val inProgress by component.inProgress.collectAsState()
        Column {
            Text(text = "Вход", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            TextField(
                value = login,
                onValueChange = component::onLoginChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                shape = RoundedCornerShape(10.dp),
            )
            TextField(
                value = password,
                onValueChange = component::onPasswordChanged,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                shape = RoundedCornerShape(10.dp),
            )
            if (inProgress)
                CircularProgressIndicator()
            else
                Button(
                    onClick = component::onSignInClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text(text = "Войти")
                }
        }
    }
}