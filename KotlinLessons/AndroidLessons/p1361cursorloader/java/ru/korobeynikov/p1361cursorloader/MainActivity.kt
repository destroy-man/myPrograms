package ru.korobeynikov.p1361cursorloader

import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.support.v4.widget.SimpleCursorAdapter
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : FragmentActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    companion object {
        private const val CM_DELETE_ID = 1

        class MyCursorLoader(context: Context, private val db: DB) : CursorLoader(context) {
            override fun loadInBackground(): Cursor {
                val cursor = db.getAllData()
                try {
                    TimeUnit.SECONDS.sleep(3)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                return cursor
            }
        }
    }

    private lateinit var db: DB
    private lateinit var scAdapter: SimpleCursorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = DB(this)
        db.open()
        val from = arrayOf(DB.COLUMN_IMG, DB.COLUMN_TXT)
        val to = arrayOf(R.id.ivImg, R.id.tvText)
        scAdapter = SimpleCursorAdapter(this, R.layout.item, null, from, to.toIntArray(), 0)
        lvData.adapter = scAdapter
        registerForContextMenu(lvData)
        supportLoaderManager.initLoader(0, null, this)
    }

    fun onButtonClick(view: View) {
        db.addRec("sometext ${scAdapter.count + 1}", R.mipmap.ic_launcher)
        supportLoaderManager.getLoader<Cursor>(0)?.forceLoad()
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu?.add(0, CM_DELETE_ID, 0, R.string.delete_record)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (item.itemId == CM_DELETE_ID) {
            val acmi = item.menuInfo as AdapterView.AdapterContextMenuInfo
            db.delRec(acmi.id)
            supportLoaderManager.getLoader<Cursor>(0)?.forceLoad()
            return true
        }
        return super.onContextItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }

    override fun onCreateLoader(p0: Int, p1: Bundle?): Loader<Cursor> {
        return MyCursorLoader(this, db)
    }

    override fun onLoadFinished(p0: Loader<Cursor>, cursor: Cursor?) {
        scAdapter.swapCursor(cursor)
    }

    override fun onLoaderReset(p0: Loader<Cursor>) {}
}