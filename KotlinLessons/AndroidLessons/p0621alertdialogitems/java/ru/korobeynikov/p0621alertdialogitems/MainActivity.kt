package ru.korobeynikov.p0621alertdialogitems

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.BaseAdapter

class MainActivity : AppCompatActivity() {

    val LOG_TAG="myLogs"
    val DIALOG_ITEMS=1
    val DIALOG_ADAPTER=2
    val DIALOG_CURSOR=3
    var cnt=0
    lateinit var db:DB
    lateinit var cursor:Cursor
    val data=arrayOf("one","two","three","four")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db=DB(this)
        db.open()
        cursor=db.getAllData()
        startManagingCursor(cursor)
    }

    fun onclick(v:View){
        changeCount()
        when(v.id){
            R.id.btnItems->showDialog(DIALOG_ITEMS)
            R.id.btnAdapter->showDialog(DIALOG_ADAPTER)
            R.id.btnCursor->showDialog(DIALOG_CURSOR)
        }
    }

    protected override fun onCreateDialog(id: Int): Dialog {
        val adb=AlertDialog.Builder(this)
        when(id){
            DIALOG_ITEMS->{
                adb.setTitle(R.string.items)
                adb.setItems(data,myClickListener)
            }
            DIALOG_ADAPTER->{
                adb.setTitle(R.string.adapter)
                val adapter=ArrayAdapter<String>(this,android.R.layout.select_dialog_item,data)
                adb.setAdapter(adapter,myClickListener)
            }
            DIALOG_CURSOR->{
                adb.setTitle(R.string.cursor)
                adb.setCursor(cursor,myClickListener,DB.COLUMN_TXT)
            }
        }
        return adb.create()
    }

    protected override fun onPrepareDialog(id: Int, dialog: Dialog?) {
        val aDialog=dialog as AlertDialog
        val lAdapter=aDialog.listView.adapter
        when(id){
            DIALOG_ITEMS,DIALOG_ADAPTER->{
                if(lAdapter is BaseAdapter){
                    val bAdapter=lAdapter
                    bAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    val myClickListener=DialogInterface.OnClickListener{dialog,which->
        Log.d(LOG_TAG,"which = $which")
    }

    fun changeCount(){
        cnt++
        data[3]=cnt.toString()
        db.changeRec(4,cnt.toString())
        cursor.requery()
    }

    protected override fun onDestroy() {
        super.onDestroy()
        db.close()
    }
}