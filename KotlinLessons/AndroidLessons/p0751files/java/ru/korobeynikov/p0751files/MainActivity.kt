package ru.korobeynikov.p0751files

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0751files.databinding.ActivityMainBinding
import java.io.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE_PERMISSION_WRITE_STORAGE = 1
        const val REQUEST_CODE_PERMISSION_READ_STORAGE = 2
    }

    private var numOperation = 0

    private val logTag = "myLogs"
    private val filename = "file"
    private val dirSd = "MyFiles"
    private val filenameSD = "fileSD"

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_PERMISSION_WRITE_STORAGE -> writeFileSD()
            REQUEST_CODE_PERMISSION_READ_STORAGE -> readFileSD()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun processPermission() {
        try {
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
            intent.addCategory("android.intent.category.DEFAULT")
            intent.data = Uri.parse(String.format("package:%s", this.packageName))
            launcherManagerStorage.launch(intent)
        } catch (e: Exception) {
            val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
            launcherManagerStorage.launch(intent)
        }
    }

    private val launcherManagerStorage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (numOperation == REQUEST_CODE_PERMISSION_WRITE_STORAGE)
                writeFileSD()
            else if (numOperation == REQUEST_CODE_PERMISSION_READ_STORAGE)
                readFileSD()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
    }

    fun clickButton(v: View) {
        when (v.id) {
            R.id.btnWrite -> writeFile()
            R.id.btnRead -> readFile()
            R.id.btnWriteSD -> {
                numOperation = REQUEST_CODE_PERMISSION_WRITE_STORAGE
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                    processPermission()
                else
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission
                        .WRITE_EXTERNAL_STORAGE), REQUEST_CODE_PERMISSION_WRITE_STORAGE)
            }
            R.id.btnReadSD -> {
                numOperation = REQUEST_CODE_PERMISSION_READ_STORAGE
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                    processPermission()
                else
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission
                        .READ_EXTERNAL_STORAGE), REQUEST_CODE_PERMISSION_READ_STORAGE)
            }
        }
    }

    private fun writeFile() {
        try {
            val bw = BufferedWriter(OutputStreamWriter(openFileOutput(filename, MODE_PRIVATE)))
            bw.write("Содержимое файла")
            bw.close()
            Log.d(logTag, "Файл записан")
        } catch (e: Exception) {
            when (e) {
                is FileNotFoundException, is IOException -> e.printStackTrace()
            }
        }
    }

    private fun readFile() {
        try {
            val br = BufferedReader(InputStreamReader(openFileInput(filename)))
            var str = br.readLine()
            while (str != null) {
                Log.d(logTag, str)
                str = br.readLine()
            }
        } catch (e: Exception) {
            when (e) {
                is FileNotFoundException, is IOException -> e.printStackTrace()
            }
        }
    }

    private fun writeFileSD() {
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            Log.d(logTag, "SD-карта не доступна: ${Environment.getExternalStorageState()}")
            return
        }
        var sdPath = Environment.getExternalStorageDirectory()
        sdPath = File("${sdPath.absolutePath}/$dirSd")
        sdPath.mkdirs()
        val sdFile = File(sdPath, filenameSD)
        try {
            val bw = BufferedWriter(FileWriter(sdFile))
            bw.write("Содержимое файла на SD")
            bw.close()
            Log.d(logTag, "Файл записан на SD: ${sdFile.absolutePath}")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun readFileSD() {
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            Log.d(logTag, "SD-карта не доступна: ${Environment.getExternalStorageState()}")
            return
        }
        var sdPath = Environment.getExternalStorageDirectory()
        sdPath = File("${sdPath.absolutePath}/$dirSd")
        val sdFile = File(sdPath, filenameSD)
        try {
            val br = BufferedReader(FileReader(sdFile))
            var str = br.readLine()
            while (str != null) {
                Log.d(logTag, str)
                str = br.readLine()
            }
        } catch (e: Exception) {
            when (e) {
                is FileNotFoundException, is IOException -> e.printStackTrace()
            }
        }
    }
}