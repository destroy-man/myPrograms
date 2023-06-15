package ru.korobeynikov.p1381location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1381location.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE_PERMISSIONS_GEOLOCATION = 1
    }

    private lateinit var tvEnabledGPS: TextView
    private lateinit var tvLocationGPS: TextView
    private lateinit var tvEnabledNet: TextView
    private lateinit var tvLocationNet: TextView
    private lateinit var locationManager: LocationManager

    private fun checkPermissions(permissions: Array<String>) = permissions.all {
        ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_PERMISSIONS_GEOLOCATION ->
                if (grantResults.isNotEmpty()) {
                    val allPermissionsGranted = grantResults.all { it == PackageManager.PERMISSION_GRANTED }
                    if (allPermissionsGranted) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            1000 * 10, 10f, locationListener)
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            1000 * 10, 10f, locationListener)
                        checkEnabled()
                    } else {
                        Toast.makeText(this, "Для продолжения работы с приложением " +
                                "необходимо получить разрешения!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        tvEnabledGPS = binding.tvEnabledGPS
        tvLocationGPS = binding.tvLocationGPS
        tvEnabledNet = binding.tvEnabledNet
        tvLocationNet = binding.tvLocationNet
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val permissions =
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (checkPermissions(permissions)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000 * 10, 10f, locationListener)
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                1000 * 10, 10f, locationListener)
            checkEnabled()
        } else
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_PERMISSIONS_GEOLOCATION)
    }

    override fun onDestroy() {
        super.onDestroy()
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
    }

    private fun showLocation(location: Location?) {
        if (location?.provider.equals(LocationManager.GPS_PROVIDER))
            tvLocationGPS.text = formatLocation(location)
        else if (location?.provider.equals(LocationManager.NETWORK_PROVIDER))
            tvLocationNet.text = formatLocation(location)
    }

    private fun formatLocation(location: Location?): String {
        if (location == null) return ""
        return String.format("Coordinates: lat = %1$.4f, lon = %2$.4f, time = %3\$tF %3\$tT",
            location.latitude, location.longitude, Date(location.time))
    }

    private fun checkEnabled() {
        tvEnabledGPS.text =
            getString(R.string.enabled, locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        tvEnabledNet.text =
            getString(R.string.enabled, locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
    }

    fun clickLocationSettings() =
        startActivity(Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS))
}