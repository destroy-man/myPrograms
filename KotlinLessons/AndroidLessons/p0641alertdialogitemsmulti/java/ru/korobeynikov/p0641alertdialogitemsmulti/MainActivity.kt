package ru.korobeynikov.p0641alertdialogitemsmulti

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import ru.korobeynikov.p0641alertdialogitemsmulti.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    private val logTag = "myLogs"
    private lateinit var db: DB
    private var cursor: Cursor? = null
    private val data = arrayOf("one", "two", "three", "four")
    private val chkd = booleanArrayOf(false, true, true, false)

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
        val adb = AlertDialog.Builder(this)
        when (v.id) {
            R.id.btnItems -> {
                adb.setTitle(R.string.items)
                adb.setMultiChoiceItems(data, chkd, myItemsMultiClickListener)
            }
            R.id.btnCursor -> {
                adb.setTitle(R.string.cursor)
                adb.setMultiChoiceItems(cursor, DB.COLUMN_CHK, DB.COLUMN_TXT, myCursorMultiClickListener)
            }
        }
        adb.setPositiveButton(R.string.ok, myBtnClickListener)
        adb.create().show()
    }

    private val myItemsMultiClickListener =
        DialogInterface.OnMultiChoiceClickListener { dialog, which, isChecked ->
            Log.d(logTag, "which = $which, isChecked = $isChecked")
        }

    private val myCursorMultiClickListener =
        DialogInterface.OnMultiChoiceClickListener { dialog, which, isChecked ->
            Log.d(logTag, "which = $which, isChecked = $isChecked")
            db.changeRec(which, isChecked)
            cursor = (LoaderManager.getInstance(this)
                .getLoader<Cursor>(0) as CursorLoader).loadInBackground()
        }

    private val myBtnClickListener = DialogInterface.OnClickListener { dialog, which ->
        val sbArray = (dialog as AlertDialog).listView.checkedItemPositions
        for (i in 0 until sbArray.size()) {
            val key = sbArray.keyAt(i)
            if (sbArray[key])
                Log.d(logTag, "checked: $key")
        }
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