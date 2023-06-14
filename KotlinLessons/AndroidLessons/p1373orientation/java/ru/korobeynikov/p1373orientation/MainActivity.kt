package ru.korobeynikov.p1373orientation

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.Surface
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1373orientation.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private val sb = StringBuilder()
    private val r = FloatArray(9)
    private val inR = FloatArray(9)
    private val outR = FloatArray(9)
    private val valuesResult = FloatArray(3)
    private val valuesResult2 = FloatArray(3)
    private var rotation = 0
    private lateinit var tvText: TextView
    private lateinit var sensorManager: SensorManager
    private lateinit var sensorAccel: Sensor
    private lateinit var sensorMagnet: Sensor
    private lateinit var timer: Timer
    val valuesAccel = FloatArray(3)
    val valuesMagnet = FloatArray(3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        tvText = binding.tvText
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorAccel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorMagnet = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(listener, sensorAccel, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(listener, sensorMagnet, SensorManager.SENSOR_DELAY_NORMAL)
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    getDeviceOrientation()
                    getActualDeviceOrientation()
                    showInfo()
                }
            }
        }, 0, 400)
        val display = display
        if (display != null)
            rotation = display.rotation
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(listener)
        timer.cancel()
    }

    private fun format(values: FloatArray) =
        String.format("%1$.1f\t\t%2$.1f\t\t%3$.1f", values[0], values[1], values[2])

    fun showInfo() {
        sb.clear()
        sb.append("Orientation: ${format(valuesResult)}\nOrientation 2: ${format(valuesResult2)}")
        tvText.text = sb
    }

    fun getDeviceOrientation() {
        SensorManager.getRotationMatrix(r, null, valuesAccel, valuesMagnet)
        SensorManager.getOrientation(r, valuesResult)
        valuesResult[0] = Math.toDegrees(valuesResult[0].toDouble()).toFloat()
        valuesResult[1] = Math.toDegrees(valuesResult[1].toDouble()).toFloat()
        valuesResult[2] = Math.toDegrees(valuesResult[2].toDouble()).toFloat()
    }

    fun getActualDeviceOrientation() {
        SensorManager.getRotationMatrix(inR, null, valuesAccel, valuesMagnet)
        var xAxis = SensorManager.AXIS_X
        var yAxis = SensorManager.AXIS_Y
        when (rotation) {
            Surface.ROTATION_0, Surface.ROTATION_90 -> {
                xAxis = SensorManager.AXIS_Y
                yAxis = SensorManager.AXIS_MINUS_X
            }
            Surface.ROTATION_180 -> yAxis = SensorManager.AXIS_MINUS_Y
            Surface.ROTATION_270 -> {
                xAxis = SensorManager.AXIS_MINUS_Y
                yAxis = SensorManager.AXIS_X
            }
        }
        SensorManager.remapCoordinateSystem(inR, xAxis, yAxis, outR)
        SensorManager.getOrientation(outR, valuesResult2)
        valuesResult2[0] = Math.toDegrees(valuesResult2[0].toDouble()).toFloat()
        valuesResult2[1] = Math.toDegrees(valuesResult2[1].toDouble()).toFloat()
        valuesResult2[2] = Math.toDegrees(valuesResult2[2].toDouble()).toFloat()
    }

    private val listener = object : SensorEventListener {

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

        override fun onSensorChanged(event: SensorEvent?) {
            when (event?.sensor?.type) {
                Sensor.TYPE_ACCELEROMETER ->
                    for (i in 0 until 3)
                        valuesAccel[i] = event.values[i]
                Sensor.TYPE_MAGNETIC_FIELD ->
                    for (i in 0 until 3)
                        valuesMagnet[i] = event.values[i]
            }
        }
    }
}