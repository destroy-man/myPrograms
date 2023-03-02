package ru.korobeynikov.p0631alertdialogitemssingle

import android.app.AlertDialog
import android.app.Dialog
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
import ru.korobeynikov.p0631alertdialogitemssingle.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    private val logTag = "myLogs"
    private lateinit var db: DB
    private var cursor: Cursor? = null
    private val data = arrayOf("one", "two", "three", "four")
    private var dialog: AlertDialog? = null

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
        if (dialog == null) {
            val adb = AlertDialog.Builder(this)
            when (v.id) {
                R.id.btnItems -> {
                    adb.setTitle(R.string.items)
                    adb.setSingleChoiceItems(data, -1, myClickListener)
                }
                R.id.btnAdapter -> {
                    adb.setTitle(R.string.adapter)
                    val adapter = ArrayAdapter(this, android.R.layout.select_dialog_singlechoice, data)
                    adb.setSingleChoiceItems(adapter, -1, myClickListener)
                }
                R.id.btnCursor -> {
                    adb.setTitle(R.string.cursor)
                    adb.setSingleChoiceItems(cursor, -1, DB.COLUMN_TXT, myClickListener)
                }
            }
            adb.setPositiveButton(R.string.ok, myClickListener)
            dialog = adb.create()
        }
        dialog?.show()
    }

    private val myClickListener = DialogInterface.OnClickListener { dialog, which ->
        val lv = (dialog as AlertDialog).listView
        if (which == Dialog.BUTTON_POSITIVE)
            Log.d(logTag, "pos = ${lv.checkedItemPosition}")
        else
            Log.d(logTag, "which = $which")
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