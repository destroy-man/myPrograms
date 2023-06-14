package ru.korobeynikov.p1371sensors

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p1371sensors.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var sensorManager: SensorManager
    private lateinit var sensors: List<Sensor>
    private lateinit var sensorLight: Sensor
    lateinit var tvText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        tvText = binding.tvText
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensors = sensorManager.getSensorList(Sensor.TYPE_ALL)
        sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    fun clickSensList() {
        sensorManager.unregisterListener(listenerLight, sensorLight)
        val sb = StringBuilder()
        for (sensor in sensors)
            sb.append("name = ${sensor.name}, type = ${sensor.type},\nvendor = ${sensor.vendor}, " +
                    "version = ${sensor.version}\nmax = ${sensor.maximumRange}, " +
                    "resolution = ${sensor.resolution}\n--------------------------------------\n")
        tvText.text = sb
    }

    fun clickSensLight() =
        sensorManager.registerListener(listenerLight, sensorLight, SensorManager.SENSOR_DELAY_NORMAL)

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(listenerLight, sensorLight)
    }

    private val listenerLight = object : SensorEventListener {

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

        override fun onSensorChanged(event: SensorEvent?) {
            tvText.text = event?.values?.get(0).toString()
        }
    }
}