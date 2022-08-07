package ru.korobeynikov.p1372acceleration

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var sensorManager: SensorManager
    private lateinit var sensorAccel: Sensor
    private lateinit var sensorLinAccel: Sensor
    private lateinit var sensorGravity: Sensor
    private val sb = StringBuilder()
    private lateinit var timer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorAccel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorLinAccel = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        sensorGravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(listener, sensorAccel, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(listener, sensorLinAccel, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(listener, sensorGravity, SensorManager.SENSOR_DELAY_NORMAL)
        timer = Timer()
        val task = object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    showInfo()
                }
            }
        }
        timer.schedule(task, 0, 400)
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
        sb.append("Accelerometer: ${format(valuesAccel)}\n\nAccel motion: ${format(valuesAccelMotion)}" +
                "\nAccel gravity: ${format(valuesAccelGravity)}\n\nLin accel: ${format(valuesLinAccel)}" +
                "\nGravity: ${format(valuesGravity)}")
        tvText.text = sb
    }

    private val valuesAccel = FloatArray(3)
    private val valuesAccelMotion = FloatArray(3)
    private val valuesAccelGravity = FloatArray(3)
    private val valuesLinAccel = FloatArray(3)
    private val valuesGravity = FloatArray(3)

    private val listener = object : SensorEventListener {
        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

        override fun onSensorChanged(event: SensorEvent?) {
            when (event?.sensor?.type) {
                Sensor.TYPE_ACCELEROMETER ->
                    for (i in 0 until 3) {
                        valuesAccel[i] = event.values[i]
                        valuesAccelGravity[i] = (0.1 * event.values[i] + 0.9 * valuesAccelGravity[i])
                            .toFloat()
                        valuesAccelMotion[i] = event.values[i] - valuesAccelGravity[i]
                    }
                Sensor.TYPE_LINEAR_ACCELERATION ->
                    for (i in 0 until 3)
                        valuesLinAccel[i] = event.values[i]
                Sensor.TYPE_GRAVITY ->
                    for (i in 0 until 3)
                        valuesGravity[i] = event.values[i]
            }
        }
    }
}