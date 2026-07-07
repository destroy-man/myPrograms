package ru.korobeynikov.p1371sensors

import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

var textState by mutableStateOf("")

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val sensorManager = context.getSystemService(SENSOR_SERVICE) as SensorManager
    val sensors = sensorManager.getSensorList(Sensor.TYPE_ALL)
    val sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Button(onClick = {
            sensorManager.unregisterListener(listenerLight, sensorLight)
            val sb = StringBuilder()
            sensors.forEach { sensor ->
                sb.append("name = ${sensor.name}")
                    .append(", type = ${sensor.type}")
                    .append("\nvendor = ${sensor.vendor}")
                    .append(", version = ${sensor.version}")
                    .append("\nmax = ${sensor.maximumRange}")
                    .append(", resolution = ${sensor.resolution}")
                    .append("\n----------\n")
            }
            textState = sb.toString()
        }) {
            Text(stringResource(R.string.list))
        }
        Button(onClick = {
            sensorManager.registerListener(
                listenerLight,
                sensorLight,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }) {
            Text(stringResource(R.string.light))
        }
        Text(textState)
    }
}

val listenerLight = object : SensorEventListener {

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    override fun onSensorChanged(event: SensorEvent?) {
        textState = event?.values[0].toString()
    }
}