package ru.korobeynikov.p1371sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var sensorManager: SensorManager
    private lateinit var sensors: List<Sensor>
    private lateinit var sensorLight: Sensor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensors = sensorManager.getSensorList(Sensor.TYPE_ALL)
        sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    fun onClickSensList(v: View) {
        sensorManager.unregisterListener(listenerLight, sensorLight)
        val sb = StringBuilder()
        for (sensor in sensors) {
            sb.append("name = ${sensor.name}, type = ${sensor.type}\nvendor = ${sensor.vendor}, " +
                    "version = ${sensor.version}\nmax = ${sensor.maximumRange}, " +
                    "resolution = ${sensor.resolution}\n--------------------------------------\n")
        }
        tvText.text = sb
    }

    fun onClickSensLight(v: View) {
        sensorManager.registerListener(listenerLight, sensorLight, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(listenerLight, sensorLight)
    }

    private val listenerLight = object : SensorEventListener {
        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

        override fun onSensorChanged(event: SensorEvent?) {
            if (event != null)
                tvText.text = event.values[0].toString()
        }
    }
}