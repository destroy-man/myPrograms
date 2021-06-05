package com.korobeynikov.p0521simplecursoradapter

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.SimpleCursorAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val CM_DELETE_ID=1
    lateinit var db:DB
    lateinit var scAdapter:SimpleCursorAdapter
    lateinit var cursor:Cursor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db=DB(this)
        db.open()
        cursor=db.getAllData()
        startManagingCursor(cursor)
        val from=arrayOf(DB.COLUMN_IMG,DB.COLUMN_TXT)
        val to=arrayOf(R.id.ivImg,R.id.tvText)
        scAdapter=SimpleCursorAdapter(this,R.layout.item,cursor,from,to.toIntArray())
        lvData.adapter=scAdapter
        registerForContextMenu(lvData)
    }

    fun onButtonClick(view: View){
        db.addRec("sometext "+(cursor.count+1),R.drawable.ic_launcher_foreground)
        cursor.requery()
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu?.add(0,CM_DELETE_ID,0,R.string.delete_record)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if(item.itemId==CM_DELETE_ID){
            val acmi=item.menuInfo as AdapterView.AdapterContextMenuInfo
            db.delRec(acmi.id)
            cursor.requery()
            return true
        }
        return super.onContextItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }
}