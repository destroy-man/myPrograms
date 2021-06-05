package com.korobeynikov.p0531simplecursortreeadapter

import android.content.Context
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SimpleCursorTreeAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var db:DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db=DB(this)
        db.open()
        val cursor=db.getCompanyData()
        startManagingCursor(cursor)
        val groupFrom=arrayOf(DB.COMPANY_COLUMN_NAME)
        val groupTo=arrayOf(android.R.id.text1)
        val childFrom=arrayOf(DB.PHONE_COLUMN_NAME)
        val childTo=arrayOf(android.R.id.text1)
        val sctAdapter=MyAdapter(this,cursor,android.R.layout.simple_expandable_list_item_1,
            groupFrom,groupTo,android.R.layout.simple_expandable_list_item_1,childFrom,childTo)
        elvMain.setAdapter(sctAdapter)
    }

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }

    inner class MyAdapter:SimpleCursorTreeAdapter{

        constructor(context:Context,cursor:Cursor,groupLayout:Int,groupFrom:Array<String>,groupTo:Array<Int>
            ,childLayout:Int,childFrom:Array<String>,childTo:Array<Int>):super(context,cursor,groupLayout,
            groupFrom,groupTo.toIntArray(),childLayout,childFrom,childTo.toIntArray())

        override fun getChildrenCursor(groupCursor: Cursor?): Cursor {
            val idColumn=groupCursor!!.getColumnIndex(DB.COMPANY_COLUMN_ID)
            return db.getPhoneData(groupCursor.getInt(idColumn).toLong())
        }

    }
}