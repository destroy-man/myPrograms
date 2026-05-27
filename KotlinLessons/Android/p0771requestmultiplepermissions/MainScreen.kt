package ru.korobeynikov.p0771requestmultiplepermissions

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val permissionsToRequest = arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            permissions.entries.forEach { permission ->
                val permissionName = permission.key
                val isGranted = permission.value
                if (isGranted) {
                    Toast.makeText(
                        context,
                        "Разрешение $permissionName получено",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "Разрешение $permissionName не получено",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    )
    Button(onClick = {
        launcher.launch(permissionsToRequest)
    }) {
        Text(stringResource(R.string.request_permissions_button_text))
    }
}