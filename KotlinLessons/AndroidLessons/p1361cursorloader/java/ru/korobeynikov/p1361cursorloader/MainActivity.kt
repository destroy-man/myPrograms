package ru.korobeynikov.p1361cursorloader

import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import ru.korobeynikov.p1361cursorloader.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    companion object {

        private const val CM_DELETE_ID = 1

        class MyCursorLoader(context: Context, private val db: DB) : CursorLoader(context) {
            override fun loadInBackground(): Cursor? {
                try {
                    TimeUnit.SECONDS.sleep(3)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                return db.getAllData()
            }
        }
    }

    private lateinit var lvData: ListView
    private lateinit var db: DB
    private lateinit var scAdapter: SimpleCursorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        db = DB(this)
        db.open()
        val from = arrayOf(DB.COLUMN_IMG, DB.COLUMN_TXT)
        val to = intArrayOf(R.id.ivImg, R.id.tvText)
        scAdapter = SimpleCursorAdapter(this, R.layout.item, null, from, to, 0)
        lvData = binding.lvData
        lvData.adapter = scAdapter
        registerForContextMenu(lvData)
        LoaderManager.getInstance(this).initLoader(0, null, this)
    }

    fun buttonClick() {
        db.addRec("sometext ${scAdapter.count + 1}", R.mipmap.ic_launcher)
        LoaderManager.getInstance(this).getLoader<String>(0)?.forceLoad()
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu?.add(0, CM_DELETE_ID, 0, R.string.delete_record)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val acmi = item.menuInfo as AdapterView.AdapterContextMenuInfo
        db.delRec(acmi.id)
        LoaderManager.getInstance(this).getLoader<String>(0)?.forceLoad()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }

    override fun onCreateLoader(id: Int, args: Bundle?) = MyCursorLoader(this, db)

    override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor?) {
        scAdapter.swapCursor(cursor)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {}
}