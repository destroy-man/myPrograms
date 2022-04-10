package ru.korobeynikov.p0751files

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import java.io.*

class MainActivity : AppCompatActivity() {

    val LOG_TAG="myLogs"
    val FILENAME="file"
    val DIR_SD="MyFiles"
    val FILENAME_SD="fileSD"
    private val REQUEST_CODE_PERMISSION_WRITE_STORAGE=1
    private val REQUEST_CODE_PERMISSION_READ_STORAGE=2
    private val REQUEST_CODE_PERMISSION_MANAGE_STORAGE=3

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            REQUEST_CODE_PERMISSION_WRITE_STORAGE->writeFileSD()
            REQUEST_CODE_PERMISSION_READ_STORAGE->readFileSD()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onclick(v:View){
        when(v.id){
            R.id.btnWrite->writeFile()
            R.id.btnRead->readFile()
            R.id.btnWriteSD->{
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
                    try{
                        val intent=Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                        intent.addCategory("android.intent.category.DEFAULT")
                        intent.data= Uri.parse(String.format("package:%s",this.packageName))
                        this.startActivityForResult(intent,REQUEST_CODE_PERMISSION_MANAGE_STORAGE)
                    }
                    catch(e:Exception){
                        val intent=Intent()
                        intent.action=Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                        this.startActivityForResult(intent,REQUEST_CODE_PERMISSION_MANAGE_STORAGE)
                    }
                }
                else
                    ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),REQUEST_CODE_PERMISSION_WRITE_STORAGE)
            }
            R.id.btnReadSD->ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_CODE_PERMISSION_READ_STORAGE)
        }
    }

    fun writeFile(){
        try{
            val bw=BufferedWriter(OutputStreamWriter(openFileOutput(FILENAME,MODE_PRIVATE)))
            bw.write("Содержимое файла")
            bw.close()
            Log.d(LOG_TAG,"Файл записан")
        }
        catch(e:Exception){
            when(e){
                is FileNotFoundException,is IOException->e.printStackTrace()
            }
        }
    }

    fun readFile(){
        try{
            val br=BufferedReader(InputStreamReader(openFileInput(FILENAME)))
            var str=br.readLine()
            while(str!=null){
                Log.d(LOG_TAG,str)
                str=br.readLine()
            }
        }
        catch(e:Exception){
            when(e){
                is FileNotFoundException,is IOException->e.printStackTrace()
            }
        }
    }

    fun writeFileSD(){
        if(Environment.getExternalStorageState()!=Environment.MEDIA_MOUNTED){
            Log.d(LOG_TAG,"SD-карта не доступна: ${Environment.getExternalStorageState()}")
            return
        }
        var sdPath=Environment.getExternalStorageDirectory()
        sdPath=File("${sdPath.absolutePath}/$DIR_SD")
        if(!sdPath.exists())
            sdPath.mkdirs()
        val sdFile=File(sdPath,FILENAME_SD)
        try{
            val bw=BufferedWriter(FileWriter(sdFile))
            bw.write("Содержимое файла на SD")
            bw.close()
            Log.d(LOG_TAG,"Файл записан на SD: ${sdFile.absolutePath}")
        }
        catch(e:IOException){
            e.printStackTrace()
        }
    }

    fun readFileSD(){
        if(Environment.getExternalStorageState()!=Environment.MEDIA_MOUNTED){
            Log.d(LOG_TAG,"SD-карта не доступна: ${Environment.getExternalStorageState()}")
            return
        }
        var sdPath=Environment.getExternalStorageDirectory()
        sdPath=File("${sdPath.absolutePath}/$DIR_SD")
        val sdFile=File(sdPath,FILENAME_SD)
        try{
            val br=BufferedReader(FileReader(sdFile))
            var str=br.readLine()
            while(str!=null){
                Log.d(LOG_TAG,str)
                str=br.readLine()
            }
        }
        catch(e:Exception){
            when(e){
                is FileNotFoundException,is IOException->e.printStackTrace()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode==REQUEST_CODE_PERMISSION_MANAGE_STORAGE)
            writeFileSD()
    }
}