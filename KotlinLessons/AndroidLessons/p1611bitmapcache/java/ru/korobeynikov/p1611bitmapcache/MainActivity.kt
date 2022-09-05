package ru.korobeynikov.p1611bitmapcache

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE_PERMISSION_READ_STORAGE = 1
    private val REQUEST_CODE_PERMISSION_MANAGE_STORAGE = 2
    private lateinit var mLvImages: ListView

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_PERMISSION_READ_STORAGE -> readData()
        }
    }

    private fun readData() {
        val dir = File(Environment.getExternalStorageDirectory(), "Download/L0161")
        val filesArray = dir.listFiles()
        if (filesArray != null) {
            val adapter = ImageAdapter(this, filesArray)
            mLvImages.adapter = adapter
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mLvImages = lvImages
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                intent.data = Uri.parse(String.format("package:%s", this.packageName))
                this.startActivityForResult(intent, REQUEST_CODE_PERMISSION_MANAGE_STORAGE)
            } catch (e: Exception) {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                this.startActivityForResult(intent, REQUEST_CODE_PERMISSION_MANAGE_STORAGE)
            }
        } else
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission
                .READ_EXTERNAL_STORAGE), REQUEST_CODE_PERMISSION_READ_STORAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_PERMISSION_MANAGE_STORAGE)
            readData()
    }

    companion object class ImageAdapter(context: Context, objects: Array<File>) :
        ArrayAdapter<File>(context, R.layout.list_item, objects) {

        private val mInflater = LayoutInflater.from(context)
        private val mPicasso = Picasso.with(context)

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var view = convertView
            if (view == null)
                view = mInflater.inflate(R.layout.list_item, parent, false)
            val imageView = view?.findViewById<ImageView>(R.id.imageView)
            mPicasso.load(getItem(position)).resizeDimen(R.dimen.image_size, R.dimen.image_size)
                .centerInside().into(imageView)
            return view!!
        }
    }
}