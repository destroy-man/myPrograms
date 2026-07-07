package ru.korobeynikov.p1372sensoracceleration

import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.Timer
import java.util.TimerTask

var textState by mutableStateOf("")

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val sensorManager = context.getSystemService(SENSOR_SERVICE) as SensorManager
    val sensorAccel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    val sensorLinAccel = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
    val sensorGravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)

    Text(text = textState, fontSize = 20.sp)

    DisposableEffect(Unit) {
        sensorManager.registerListener(
            listener,
            sensorAccel,
            SensorManager.SENSOR_DELAY_NORMAL
        )
        sensorManager.registerListener(
            listener,
            sensorLinAccel,
            SensorManager.SENSOR_DELAY_NORMAL
        )
        sensorManager.registerListener(
            listener,
            sensorGravity,
            SensorManager.SENSOR_DELAY_NORMAL
        )
        val timer = Timer()
        val task = object : TimerTask() {
            override fun run() {
                scope.launch(Dispatchers.Main) {
                    showInfo()
                }
            }
        }
        timer.schedule(task, 0, 1000)

        onDispose {
            sensorManager.unregisterListener(listener)
            scope.cancel()
            timer.cancel()
        }
    }
}

fun format(values: FloatArray): String {
    return String.format(
        Locale.getDefault(),
        "%1$.1f\t\t%2$.1f\t\t%3$.1f", values[0], values[1], values[2]
    )
}

fun showInfo() {
    val sb = StringBuilder()
    sb.append("Accelerometer: ${format(valuesAccel)}")
        .append("\n\nAccel motion: ${format(valuesAccelMotion)}")
        .append("\nAccel gravity: ${format(valuesAccelGravity)}")
        .append("\n\nLin accel: ${format(valuesLinAccel)}")
        .append("\nGravity: ${format(valuesGravity)}")
    textState = sb.toString()
}

val valuesAccel = FloatArray(3)
val valuesAccelMotion = FloatArray(3)
val valuesAccelGravity = FloatArray(3)
val valuesLinAccel = FloatArray(3)
val valuesGravity = FloatArray(3)

val listener = object : SensorEventListener {

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    override fun onSensorChanged(event: SensorEvent?) {
        when (event?.sensor?.type) {
            Sensor.TYPE_ACCELEROMETER -> {
                for (i in 0..2) {
                    valuesAccel[i] = event.values[i]
                    valuesAccelGravity[i] =
                        (0.1 * event.values[i] + 0.9 * valuesAccelGravity[i]).toFloat()
                    valuesAccelMotion[i] = event.values[i] - valuesAccelGravity[i]
                }
            }

            Sensor.TYPE_LINEAR_ACCELERATION -> {
                for (i in 0..2)
                    valuesLinAccel[i] = event.values[i]
            }

            Sensor.TYPE_GRAVITY -> {
                for (i in 0..2)
                    valuesGravity[i] = event.values[i]
            }
        }
    }
}