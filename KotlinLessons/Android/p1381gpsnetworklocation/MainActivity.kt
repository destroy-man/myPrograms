package ru.korobeynikov.p1381gpsnetworklocation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
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
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val fineLocationStatus = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            val coarseLocationStatus = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            var hasPermissions by remember {
                mutableStateOf(
                    fineLocationStatus == PackageManager.PERMISSION_GRANTED &&
                            coarseLocationStatus == PackageManager.PERMISSION_GRANTED
                )
            }

            Column(modifier = Modifier.safeContentPadding()) {
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestMultiplePermissions(),
                    onResult = { permissions ->
                        hasPermissions = true
                        permissions.forEach { (_, status) ->
                            if (!status) {
                                hasPermissions = false
                                return@forEach
                            }
                        }
                    }
                )

                if (hasPermissions) {
                    MainScreen()
                } else {
                    Button(onClick = {
                        launcher.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        )
                    }) {
                        Text(stringResource(R.string.location_permission_button))
                    }
                }
            }
        }
    }
}