package ru.korobeynikov.p1341camerafeatures

import android.Manifest
import android.content.pm.PackageManager
import android.hardware.Camera
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.SurfaceHolder
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var camera: Camera? = null
    private val REQUEST_CODE_PERMISSION_CAMERA = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),
                REQUEST_CODE_PERMISSION_CAMERA)
        val holder = surfaceView.holder
        holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(p0: SurfaceHolder) {
                try {
                    camera?.setPreviewDisplay(holder)
                    camera?.startPreview()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {}

            override fun surfaceDestroyed(p0: SurfaceHolder) {}
        })
    }

    override fun onResume() {
        super.onResume()
        camera = Camera.open()
        initSpinners()
    }

    override fun onPause() {
        super.onPause()
        if (camera != null)
            camera?.release()
        camera = null
    }

    private fun initSpinners() {
        if (camera != null) {
            val colorEffects = camera!!.parameters.supportedColorEffects
            val spEffect = initSpinner(R.id.spEffect, colorEffects, camera!!.parameters.colorEffect)
            spEffect.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(arg0: AdapterView<*>?, arg1: View?, arg2: Int, arg3: Long) {
                    val params = camera?.parameters
                    params?.colorEffect = colorEffects[arg2]
                    camera?.parameters = params
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
            val flashModes = camera!!.parameters.supportedFlashModes
            val spFlash = initSpinner(R.id.spFlash, flashModes, camera!!.parameters.flashMode)
            spFlash.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(arg0: AdapterView<*>?, arg1: View?, arg2: Int, arg3: Long) {
                    val params = camera?.parameters
                    params?.flashMode = flashModes[arg2]
                    camera?.parameters = params
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }
    }

    private fun initSpinner(spinnerId: Int, data: List<String>, currentValue: String): Spinner {
        val spinner = findViewById<Spinner>(spinnerId)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, data)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        for (i in data.indices) {
            val item = data[i]
            if (item == currentValue)
                spinner.setSelection(i)
        }
        return spinner
    }
}