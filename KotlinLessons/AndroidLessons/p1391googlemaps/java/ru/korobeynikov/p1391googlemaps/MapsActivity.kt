package ru.korobeynikov.p1391googlemaps

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import ru.korobeynikov.p1391googlemaps.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private var map: GoogleMap? = null
    private val tag = "myLogs"
    private lateinit var mapFragment: SupportMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMapsBinding>(this, R.layout.activity_maps)
        binding.view = this
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        if (map == null)
            finish()
        init()
    }

    private fun init() {
        map?.setOnMapClickListener { latLng ->
            Log.d(tag, "onMapClick: ${latLng.latitude},${latLng.longitude}")
        }
        map?.setOnMapLongClickListener { latLng ->
            Log.d(tag, "onMapLongClick: ${latLng.latitude},${latLng.longitude}")
        }
        map?.setOnCameraIdleListener {
            val latitude = map?.cameraPosition?.target?.latitude
            val longitude = map?.cameraPosition?.target?.longitude
            Log.d(tag, "onCameraIdle: $latitude,$longitude")
        }
    }

    fun clickTest() {
        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(
            LatLngBounds(LatLng(-39.0, 112.0), LatLng(-11.0, 154.0)), 100)
        map?.animateCamera(cameraUpdate)
    }
}