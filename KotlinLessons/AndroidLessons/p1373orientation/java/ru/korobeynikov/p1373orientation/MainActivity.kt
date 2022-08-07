package ru.korobeynikov.p1373orientation

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Surface
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var sensorManager: SensorManager
    private lateinit var sensorAccel: Sensor
    private lateinit var sensorMagnet: Sensor
    private val sb = StringBuilder()
    private lateinit var timer: Timer
    private var rotation = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorAccel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorMagnet = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(listener, sensorAccel, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(listener, sensorMagnet, SensorManager.SENSOR_DELAY_NORMAL)
        timer = Timer()
        val task = object : TimerTask() {
            override fun run() {
                getDeviceOrientation()
                getActualDeviceOrientation()
                runOnUiThread {
                    showInfo()
                }
            }
        }
        timer.schedule(task, 0, 400)
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        rotation = display.rotation
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(listener)
        timer.cancel()
    }

    private fun format(values: FloatArray): String {
        return String.format("%1$.1f\t\t%2$.1f\t\t%3$.1f", values[0], values[1], values[2])
    }

    private fun showInfo() {
        sb.setLength(0)
        sb.append("Orientation: ${format(valuesResult)}\nOrientation 2: ${format(valuesResult2)}")
        tvText.text = sb
    }

    private val r = FloatArray(9)

    private fun getDeviceOrientation() {
        SensorManager.getRotationMatrix(r, null, valuesAccel, valuesMagnet)
        SensorManager.getOrientation(r, valuesResult)
        valuesResult[0] = Math.toDegrees(valuesResult[0].toDouble()).toFloat()
        valuesResult[1] = Math.toDegrees(valuesResult[1].toDouble()).toFloat()
        valuesResult[2] = Math.toDegrees(valuesResult[2].toDouble()).toFloat()
    }

    private val inR = FloatArray(9)
    private val outR = FloatArray(9)

    private fun getActualDeviceOrientation() {
        SensorManager.getRotationMatrix(inR, null, valuesAccel, valuesMagnet)
        var x_axis = SensorManager.AXIS_X
        var y_axis = SensorManager.AXIS_Y
        when (rotation) {
            Surface.ROTATION_90 -> {
                x_axis = SensorManager.AXIS_Y
                y_axis = SensorManager.AXIS_MINUS_X
            }
            Surface.ROTATION_180 -> y_axis = SensorManager.AXIS_MINUS_Y
            Surface.ROTATION_270 -> {
                x_axis = SensorManager.AXIS_MINUS_Y
                y_axis = SensorManager.AXIS_X
            }
        }
        SensorManager.remapCoordinateSystem(inR, x_axis, y_axis, outR)
        SensorManager.getOrientation(outR, valuesResult2)
        valuesResult2[0] = Math.toDegrees(valuesResult2[0].toDouble()).toFloat()
        valuesResult2[1] = Math.toDegrees(valuesResult2[1].toDouble()).toFloat()
        valuesResult2[2] = Math.toDegrees(valuesResult2[2].toDouble()).toFloat()
    }

    private val valuesAccel = FloatArray(3)
    private val valuesMagnet = FloatArray(3)
    private val valuesResult = FloatArray(3)
    private val valuesResult2 = FloatArray(3)

    private val listener = object : SensorEventListener {
        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

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