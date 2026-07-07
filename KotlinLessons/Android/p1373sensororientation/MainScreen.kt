package ru.korobeynikov.p1373sensororientation

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.view.Surface
import android.view.WindowManager
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
var rotation = 0

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val sensorManager = context.getSystemService(SENSOR_SERVICE) as SensorManager
    val sensorAccel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    val sensorMagnet = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

    Text(textState, fontSize = 20.sp)

    DisposableEffect(Unit) {
        sensorManager.registerListener(
            listener,
            sensorAccel,
            SensorManager.SENSOR_DELAY_NORMAL
        )
        sensorManager.registerListener(
            listener,
            sensorMagnet,
            SensorManager.SENSOR_DELAY_NORMAL
        )
        val timer = Timer()
        val task = object : TimerTask() {
            override fun run() {
                scope.launch(Dispatchers.Main) {
                    getDeviceOrientation()
                    getActualDeviceOrientation()
                    showInfo()
                }
            }
        }
        timer.schedule(task, 0, 400)
        val display = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            context.display
        } else {
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            windowManager.defaultDisplay
        }
        rotation = display.rotation

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
        "%1$.1f\t\t%2$.1f\t\t%3$.1f",
        values[0],
        values[1],
        values[2]
    )
}

fun showInfo() {
    val sb = StringBuilder()
    sb.append("Orientation: ${format(valuesResult)}")
        .append("\nOrientation 2: ${format(valuesResult2)}")
    textState = sb.toString()
}

val r = FloatArray(9)

fun getDeviceOrientation() {
    SensorManager.getRotationMatrix(r, null, valuesAccel, valuesMagnet)
    SensorManager.getOrientation(r, valuesResult)
    valuesResult[0] = Math.toDegrees(valuesResult[0].toDouble()).toFloat()
    valuesResult[1] = Math.toDegrees(valuesResult[1].toDouble()).toFloat()
    valuesResult[2] = Math.toDegrees(valuesResult[2].toDouble()).toFloat()
}

val inR = FloatArray(9)
val outR = FloatArray(9)

fun getActualDeviceOrientation() {
    SensorManager.getRotationMatrix(inR, null, valuesAccel, valuesMagnet)
    var x_axis = SensorManager.AXIS_X
    var y_axis = SensorManager.AXIS_Y
    when (rotation) {
        Surface.ROTATION_0, Surface.ROTATION_90 -> {
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

val valuesAccel = FloatArray(3)
val valuesMagnet = FloatArray(3)
val valuesResult = FloatArray(3)
val valuesResult2 = FloatArray(3)

val listener = object : SensorEventListener {

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    override fun onSensorChanged(event: SensorEvent?) {
        when (event?.sensor?.type) {
            Sensor.TYPE_ACCELEROMETER -> {
                for (i in 0..2)
                    valuesAccel[i] = event.values[i]
            }

            Sensor.TYPE_MAGNETIC_FIELD -> {
                for (i in 0..2)
                    valuesMagnet[i] = event.values[i]
            }
        }
    }
}