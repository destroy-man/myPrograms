package com.korobeynikov.p0751files

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import java.io.*

class MainActivity : AppCompatActivity() {

    val LOG_TAG="myLogs"
    val FILENAME="file"
    val DIR_SD="myFiles"
    val FILENAME_SD="fileSD"

    private val REQUEST_CODE_PERMISSION_WRITE_STORAGE=1
    private val REQUEST_CODE_PERMISSION_READ_STORAGE=2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onclick(v:View){
        when(v.id){
            R.id.btnWrite->writeFile()
            R.id.btnRead->readFile()
            R.id.btnWriteSD->writeFileSD()
            R.id.btnReadSD->readFileSD()
        }
    }

    fun writeFile(){
        try{
            val bw=BufferedWriter(OutputStreamWriter(openFileOutput(FILENAME,MODE_PRIVATE)))
            bw.write("Содержимое файла")
            bw.close()
            Log.d(LOG_TAG,"Файл записан")
        }
        catch(e:FileNotFoundException){
            e.printStackTrace()
        }
        catch(e:IOException){
            e.printStackTrace()
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
        catch(e:FileNotFoundException){
            e.printStackTrace()
        }
        catch(e:IOException){
            e.printStackTrace()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            REQUEST_CODE_PERMISSION_WRITE_STORAGE->{
                if(grantResults.size>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    var sdPath=Environment.getExternalStorageDirectory()
                    sdPath=File(sdPath.absolutePath+"/"+DIR_SD)
                    sdPath.mkdirs()
                    val sdFile=File(sdPath,FILENAME_SD)
                    try{
                        val bw=BufferedWriter(FileWriter(sdFile))
                        bw.write("Содержимое файла на SD")
                        bw.close()
                        Log.d(LOG_TAG,"Файл записан на SD: "+sdFile.absolutePath)
                    }
                    catch(e:IOException){
                        e.printStackTrace()
                    }
                }
            }
            REQUEST_CODE_PERMISSION_READ_STORAGE->{
                if(grantResults.size>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    var sdPath=Environment.getExternalStorageDirectory()
                    sdPath=File(sdPath.absolutePath+"/"+DIR_SD)
                    sdPath.mkdirs()
                    val sdFile=File(sdPath,FILENAME_SD)
                    try{
                        val br=BufferedReader(FileReader(sdFile))
                        var str=br.readLine()
                        while(str!=null){
                            Log.d(LOG_TAG,str)
                            str=br.readLine()
                        }
                    }
                    catch(e:FileNotFoundException){
                        e.printStackTrace()
                    }
                    catch (e:Resources.NotFoundException){
                        e.printStackTrace()
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun writeFileSD(){
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            Log.d(LOG_TAG,"SD-карта не доступна: "+Environment.getExternalStorageState())
            return
        }
        ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),REQUEST_CODE_PERMISSION_WRITE_STORAGE)
    }

    fun readFileSD(){
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            Log.d(LOG_TAG,"SD-карта не доступна: "+Environment.getExternalStorageState())
            return
        }
        ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),REQUEST_CODE_PERMISSION_READ_STORAGE)
    }
}