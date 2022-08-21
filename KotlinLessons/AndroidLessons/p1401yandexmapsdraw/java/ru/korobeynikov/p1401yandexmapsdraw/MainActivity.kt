package ru.korobeynikov.p1401yandexmapsdraw

import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.*
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.runtime.ui_view.ViewProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.setApiKey(getString(R.string.api_key))
        MapKitFactory.initialize(this)

        setContentView(R.layout.activity_main)

        mapView.map.move(CameraPosition(Point(0.0, 0.0), 11f, 0f,
            0f), Animation(Animation.Type.SMOOTH, 0f), null)
        init()
    }

    private fun init() {
        val points = ArrayList<Point>()
        points.add(Point(-4.0, -5.0))
        points.add(Point(0.0, -1.0))
        points.add(Point(4.0, -5.0))
        points.add(Point(0.0, -9.0))
        var polygoneOptions = Polygon(LinearRing(points), arrayListOf())
        val polygonHole = mapView.map.mapObjects.addPolygon(polygoneOptions)
        polygonHole.zIndex = 2f

        points.clear()
        points.add(Point(-5.0, -10.0))
        points.add(Point(-5.0, 0.0))
        points.add(Point(5.0, 0.0))
        points.add(Point(5.0, -10.0))
        polygoneOptions = Polygon(LinearRing(points), arrayListOf())
        val polygon = mapView.map.mapObjects.addPolygon(polygoneOptions)
        polygon.zIndex = 1f
        polygon.strokeColor = Color.CYAN
        polygon.strokeWidth = 1f
        polygon.fillColor = Color.GREEN
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
        mapView.map.mapObjects.addPlacemark(Point(0.0, 0.0))
            .addTapListener{ mapObject, point ->
                val textView = TextView(this)
                textView.text = "Hello world\nAdditional text"
                mapView.map.mapObjects.addPlacemark(Point(0.02, 0.0), ViewProvider(textView))
                false
            }
    }
}