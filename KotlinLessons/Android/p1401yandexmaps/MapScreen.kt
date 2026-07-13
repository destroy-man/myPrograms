package ru.korobeynikov.p1401yandexmaps

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView

@Composable
fun MapScreen() {
    val context = LocalContext.current
    MapKitFactory.setApiKey(BuildConfig.YANDEX_MAPS_API_KEY)
    var mapView by remember { mutableStateOf<MapView?>(null) }

    Scaffold(Modifier.fillMaxSize()) { paddings ->
        AndroidView(
            factory = { MapView(it) },
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings)
        ) {
            mapView = it
            mapView?.map?.move(
                CameraPosition(
                    Point(55.751225, 37.62954),
                    17.0f,
                    150.0f,
                    30.0f
                )
            )
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { mapView }.collect { mapView ->
            mapView?.let {
                MapKitFactory.initialize(context)
                MapKitFactory.getInstance().onStart()
                mapView.onStart()
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            mapView?.let { mapView ->
                mapView.onStop()
                MapKitFactory.getInstance().onStop()
            }
        }
    }
}