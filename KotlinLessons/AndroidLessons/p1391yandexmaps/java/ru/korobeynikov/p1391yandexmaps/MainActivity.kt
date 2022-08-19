package ru.korobeynikov.p1391yandexmaps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.*
import com.yandex.mapkit.map.Map
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = "myLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.setApiKey(getString(R.string.api_key))
        MapKitFactory.initialize(this)

        setContentView(R.layout.activity_main)

        mapView.map.move(CameraPosition(Point(55.751574, 37.573856), 11f,
            0f, 0f), Animation(Animation.Type.SMOOTH, 0f), null)
        init()
    }

    private fun init() {
        mapView.map.addInputListener(inputListener)
        mapView.map.addCameraListener(cameraListener)
    }

    private val inputListener = object : InputListener {
        override fun onMapTap(p0: Map, latLng: Point) {
            Log.d(TAG, "onMapCLick: ${latLng.latitude}, ${latLng.longitude}")
        }

        override fun onMapLongTap(p0: Map, latLng: Point) {
            Log.d(TAG, "onMapLongClick: ${latLng.latitude}, ${latLng.longitude}")
        }
    }

    private val cameraListener = CameraListener { p0, cameraPosition, p2, p3 ->
        Log.d(TAG, "onCameraChange: ${cameraPosition.target.latitude}, ${cameraPosition.target
            .longitude}")
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    fun onClickTest(view: View) {
        mapView.map.move(CameraPosition(Point(-27.0, 133.0), 5f, 45f,
            20f), Animation(Animation.Type.SMOOTH, 0f), null)
    }
}