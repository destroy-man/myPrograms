package ru.korobeynikov.p1381location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var locationManager: LocationManager
    private val REQUEST_CODE_PERMISSION_FINE_LOCATION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (android.os.Build.VERSION.SDK_INT >= 23)
            if (this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
                this.requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_CODE_PERMISSION_FINE_LOCATION)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    @SuppressLint("MissingPermission")
    override fun onResume() {
        super.onResume()
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 10, 10f,
            locationListener)
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 * 10, 10f,
            locationListener)
        checkEnabled()
    }

    override fun onPause() {
        super.onPause()
        locationManager.removeUpdates(locationListener)
    }

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            showLocation(location)
        }

        override fun onProviderDisabled(provider: String) {
            checkEnabled()
        }

        @SuppressLint("MissingPermission")
        override fun onProviderEnabled(provider: String) {
            checkEnabled()
            showLocation(locationManager.getLastKnownLocation(provider))
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            if (provider == LocationManager.GPS_PROVIDER)
                tvStatusGPS.text = "Status: $status"
            else if (provider == LocationManager.NETWORK_PROVIDER)
                tvStatusNet.text = "Status: $status"
        }
    }

    private fun showLocation(location: Location?) {
        if (location == null)
            return
        if (location.provider == LocationManager.GPS_PROVIDER)
            tvLocationGPS.text = formatLocation(location)
        else if (location.provider == LocationManager.NETWORK_PROVIDER)
            tvLocationNet.text = formatLocation(location)
    }

    private fun formatLocation(location: Location): String {
        if (location == null)
            return ""
        val lat = String.format("%1$.4f", location.latitude)
        val lon = String.format("%1$.4f", location.longitude)
        val dateFormat = SimpleDateFormat("y-MM-dd HH:mm:ss")
        val date = dateFormat.format(Date(location.time))
        return "Coordinates: lat = $lat, lon = $lon, time = $date"
    }

    private fun checkEnabled() {
        tvEnabledGPS.text =
            "Enabled: ${locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)}"
        tvEnabledNet.text =
            "Enabled: ${locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)}"
    }

    fun onClickLocationSettings(view: View) {
        startActivity(Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS))
    }
}