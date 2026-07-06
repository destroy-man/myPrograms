package ru.korobeynikov.p1311camera

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Column(modifier = Modifier.safeContentPadding()) {
                val context = LocalContext.current
                val permissionStatus =
                    ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                var isActiveCamera by remember {
                    mutableStateOf(permissionStatus == PackageManager.PERMISSION_GRANTED)
                }
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        isActiveCamera = isGranted
                    }
                )

                if (isActiveCamera) {
                    CameraScreen()
                } else {
                    Button(onClick = {
                        launcher.launch(Manifest.permission.CAMERA)
                    }) {
                        Text(stringResource(R.string.button_text_camera))
                    }
                }
            }
        }
    }
}