package ru.korobeynikov.p1391googlemaps

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState

@Composable
fun MapScreen() {
    val singapore = LatLng(1.35, 103.87)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }
    var uiSettings by remember { mutableStateOf(MapUiSettings()) }
    var properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.SATELLITE))
    }
    Box {
        GoogleMap(
            cameraPositionState = cameraPositionState,
            properties = properties,
            uiSettings = uiSettings,
            modifier = Modifier.fillMaxSize()
        ) {
            Marker(
                state = rememberUpdatedMarkerState(singapore),
                title = "Singapore",
                snippet = "Marker in Singapore"
            )
            Polyline(
                points = listOf(
                    LatLng(1.35, 103.87),
                    LatLng(2.35, 103.87),
                    LatLng(2.35, 104.87),
                    LatLng(3.35, 104.87),
                    LatLng(4.35, 105.87)
                )
            )
        }
        Switch(checked = uiSettings.zoomControlsEnabled, onCheckedChange = {
            uiSettings = uiSettings.copy(zoomControlsEnabled = it)
        })
    }
}

@Composable
fun SimpleMapScreen() {
    val singapore = LatLng(1.35, 103.87)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }
    GoogleMap(cameraPositionState = cameraPositionState, modifier = Modifier.fillMaxSize()) {
        Marker(
            state = rememberUpdatedMarkerState(singapore),
            title = "Singapore",
            snippet = "Marker in Singapore"
        )
    }
}