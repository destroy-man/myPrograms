package ru.korobeynikov.p0761requestpermission

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
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                Toast.makeText(context, "Можно работать с камерой", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Нельзя работать с камерой", Toast.LENGTH_SHORT).show()
            }
        }
    )
    Button(onClick = {
        launcher.launch(Manifest.permission.CAMERA)
    }) {
        Text(stringResource(R.string.request_permission_button_text))
    }
}