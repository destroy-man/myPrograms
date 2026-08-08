package ru.korobeynikov.p185notificationsactivityopeningmodes.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val permission = Manifest.permission.POST_NOTIFICATIONS
            val context = LocalContext.current
            var hasPermission by remember {
                mutableStateOf(
                    context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
                )
            }

            val launcher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                hasPermission = isGranted
            }

            Column(modifier = Modifier.safeContentPadding()) {
                if (hasPermission) {
                    MainScreen()
                } else {
                    Button(onClick = {
                        launcher.launch(permission)
                    }) {
                        Text("Получить разрешение на отправку уведомлений")
                    }
                }
            }
        }
    }
}