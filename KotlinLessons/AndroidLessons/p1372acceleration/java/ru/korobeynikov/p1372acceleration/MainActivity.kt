package ru.korobeynikov.p1372acceleration

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1372acceleration.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private val sb = StringBuilder()
    private lateinit var tvText: TextView
    private lateinit var sensorManager: SensorManager
    private lateinit var sensorAccel: Sensor
    private lateinit var sensorLinAccel: Sensor
    private lateinit var sensorGravity: Sensor
    private lateinit var timer: Timer
    val valuesAccel = FloatArray(3)
    val valuesAccelMotion = FloatArray(3)
    val valuesAccelGravity = FloatArray(3)
    val valuesLinAccel = FloatArray(3)
    val valuesGravity = FloatArray(3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        tvText = binding.tvText
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
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
        timer.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    showInfo()
                }
            }
        }, 0, 400)
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
        sb.append("Accelerometer: ${format(valuesAccel)}\n\n" +
                "Accel motion: ${format(valuesAccelMotion)}\n" +
                "Accel gravity: ${format(valuesAccelGravity)}\n\n" +
                "Lin accel: ${format(valuesLinAccel)}\nGravity: ${format(valuesGravity)}")
        tvText.text = sb
    }

    private val listener = object : SensorEventListener {

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

        override fun onSensorChanged(event: SensorEvent?) {
            when (event?.sensor?.type) {
                Sensor.TYPE_ACCELEROMETER ->
                    for (i in 0 until 3) {
                        valuesAccel[i] = event.values[i]
                        valuesAccelGravity[i] = 0.1f * event.values[i] + 0.9f * valuesAccelGravity[i]
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