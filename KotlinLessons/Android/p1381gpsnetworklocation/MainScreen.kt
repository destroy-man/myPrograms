package ru.korobeynikov.p1381gpsnetworklocation

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import java.util.Date
import java.util.Locale

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager
    var enableGps by remember { mutableStateOf("") }
    var locationGps by remember { mutableStateOf("") }
    var enableNet by remember { mutableStateOf("") }
    var locationNet by remember { mutableStateOf("") }

    val onChangeEnableGps = {
        enableGps = "Enabled: ${locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)}"
    }
    val onChangeEnableNet = {
        enableNet =
            "Enabled: ${locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)}"
    }
    val onChangeLocationGps: (Location) -> Unit = { location ->
        locationGps = formatLocation(location) ?: ""
    }
    val onChangeLocationNet: (Location) -> Unit = { location ->
        locationNet = formatLocation(location) ?: ""
    }

    val locationListener = object : LocationListener {

        override fun onLocationChanged(location: Location) {
            showLocation(location, onChangeLocationGps, onChangeLocationNet)
        }

        override fun onProviderDisabled(provider: String) {
            checkEnabled(onChangeEnableGps, onChangeEnableNet)
        }

        @RequiresPermission(
            allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION]
        )
        override fun onProviderEnabled(provider: String) {
            checkEnabled(onChangeEnableGps, onChangeEnableNet)
            showLocation(
                locationManager.getLastKnownLocation(provider),
                onChangeLocationGps,
                onChangeLocationNet
            )
        }
    }

    Column(modifier = Modifier.padding(5.dp)) {
        Text(stringResource(R.string.provider_gps), fontSize = 30.sp)
        Text(enableGps, fontSize = 24.sp)
        Text(locationGps, fontSize = 24.sp)

        Text(
            stringResource(
                R.string.provider_network
            ),
            fontSize = 30.sp,
            modifier = Modifier.padding(top = 10.dp)
        )
        Text(enableNet, fontSize = 24.sp)
        Text(locationNet, fontSize = 24.sp)

        Button(modifier = Modifier.padding(top = 10.dp), onClick = {
            context.startActivity(
                Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            )
        }) {
            Text(stringResource(R.string.location_settings))
        }
    }

    DisposableEffect(Unit) {
        val fineLocationStatus =
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarseLocationStatus = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (
            fineLocationStatus == PackageManager.PERMISSION_GRANTED
                && coarseLocationStatus == PackageManager.PERMISSION_GRANTED
        ) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1000 * 10L,
                10f,
                locationListener
            )
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                1000 * 10L,
                10f,
                locationListener
            )
            checkEnabled(onChangeEnableGps, onChangeEnableNet)
        }

        onDispose {
            locationManager.removeUpdates(locationListener)
        }
    }
}

private fun showLocation(
    location: Location?,
    onChangeLocationGps: (Location) -> Unit,
    onChangeLocationNet: (Location) -> Unit
) {
    location?.let {
        if (location.provider == LocationManager.GPS_PROVIDER) {
            onChangeLocationGps(location)
        } else if (location.provider == LocationManager.NETWORK_PROVIDER) {
            onChangeLocationNet(location)
        }
    }
}

private fun formatLocation(location: Location?): String? {
    location?.let {
        return String.format(
            Locale.getDefault(),
            "Coordinates: lat = %1$.4f, lon = %2$.4f, time = %3\$tF %3\$tT",
            location.latitude,
            location.longitude,
            Date(location.time)
        )
    }
    return null
}

private fun checkEnabled(onChangeEnableGps: () -> Unit, onChangeEnableNet: () -> Unit) {
    onChangeEnableGps()
    onChangeEnableNet()
}