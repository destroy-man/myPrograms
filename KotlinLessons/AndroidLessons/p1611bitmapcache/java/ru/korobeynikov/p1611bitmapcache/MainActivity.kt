package ru.korobeynikov.p1611bitmapcache

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.korobeynikov.p1611bitmapcache.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    companion object {

        const val REQUEST_CODE_PERMISSION_READ_STORAGE = 1

        class ImageAdapter(private val dataSet: Array<File>) :
            RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

            private val mPicasso = Picasso.get()

            class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

                val imageView: ImageView

                init {
                    imageView = view.findViewById(R.id.imageView)
                }
            }

            override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
                val view =
                    LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item, viewGroup, false)
                return ViewHolder(view)
            }

            override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
                mPicasso.load(dataSet[position]).resizeDimen(R.dimen.image_size, R.dimen.image_size)
                    .centerInside().into(viewHolder.imageView)
            }

            override fun getItemCount() = dataSet.size
        }
    }

    private lateinit var binding: ActivityMainBinding

    private fun checkPermissions(permissions: Array<String>) = permissions.all {
        ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_PERMISSION_READ_STORAGE -> {
                if (grantResults.isNotEmpty()) {
                    val allPermissionsGranted = grantResults.all { it == PackageManager.PERMISSION_GRANTED }
                    if (allPermissionsGranted)
                        readFile()
                    else {
                        Toast.makeText(this, "Для продолжения работы с приложением " +
                                "необходимо получить разрешение!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
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
            readFile()
        }

    private fun readFile() {
        val dir = File(Environment.getExternalStorageDirectory(), "Download/L0161")
        val rvImages = binding.rvImages
        val layoutManager = LinearLayoutManager(this)
        rvImages.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(rvImages.context, layoutManager.orientation)
        rvImages.addItemDecoration(dividerItemDecoration)
        rvImages.adapter = ImageAdapter(dir.listFiles() as Array<File>)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager())
                readFile()
            else
                processPermission()
        } else {
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            if (checkPermissions(permissions))
                readFile()
            else
                ActivityCompat
                    .requestPermissions(this, permissions, REQUEST_CODE_PERMISSION_READ_STORAGE)
        }
    }
}