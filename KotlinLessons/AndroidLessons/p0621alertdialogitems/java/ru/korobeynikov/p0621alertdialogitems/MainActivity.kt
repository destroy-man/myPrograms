package ru.korobeynikov.p0621alertdialogitems

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import ru.korobeynikov.p0621alertdialogitems.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    private var cnt = 0
    private lateinit var db: DB
    private var cursor: Cursor? = null
    private val data = arrayOf("one", "two", "three", "four")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        db = DB(this)
        db.open()
        cursor = (LoaderManager.getInstance(this)
            .initLoader(0, null, this) as CursorLoader).loadInBackground()
    }

    fun clickButton(v: View) {
        changeCount()
        val adb = AlertDialog.Builder(this)
        when (v.id) {
            R.id.btnItems -> {
                adb.setTitle(R.string.items)
                adb.setItems(data, myClickListener)
            }
            R.id.btnAdapter -> {
                adb.setTitle(R.string.adapter)
                val adapter = ArrayAdapter(this, android.R.layout.select_dialog_item, data)
                adb.setAdapter(adapter, myClickListener)
            }
            R.id.btnCursor -> {
                adb.setTitle(R.string.cursor)
                cursor = (LoaderManager.getInstance(this)
                    .getLoader<Cursor>(0) as CursorLoader).loadInBackground()
                adb.setCursor(cursor, myClickListener, DB.COLUMN_TEXT)
            }
        }
        adb.create().show()
    }

    private val myClickListener = DialogInterface.OnClickListener { dialog, which ->
        Log.d("myLogs", "which = $which")
    }

    private fun changeCount() {
        cnt++
        data[3] = cnt.toString()
        db.changeRec(4, cnt.toString())
        cursor = (LoaderManager.getInstance(this).getLoader<Cursor>(0) as CursorLoader)
            .loadInBackground()
    }

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }

    override fun onCreateLoader(id: Int, args: Bundle?) = MyCursorLoader(this, db)

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {}

    override fun onLoaderReset(loader: Loader<Cursor>) {}

    class MyCursorLoader(context: Context, private val db: DB) : CursorLoader(context) {
        override fun loadInBackground() = db.getAllData()
    }
}