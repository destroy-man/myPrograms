package ru.korobeynikov.p0631alertdialogitemssingle

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter

class MainActivity : AppCompatActivity() {

    val LOG_TAG="myLogs"
    val DIALOG_ITEMS=1
    val DIALOG_ADAPTER=2
    val DIALOG_CURSOR=3
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
        when(v.id){
            R.id.btnItems->showDialog(DIALOG_ITEMS)
            R.id.btnAdapter->showDialog(DIALOG_ADAPTER)
            R.id.btnCursor->showDialog(DIALOG_CURSOR)
        }
    }

    override fun onPrepareDialog(id: Int, dialog: Dialog?) {
        (dialog as AlertDialog).listView.setItemChecked(2,true)
    }

    protected override fun onCreateDialog(id: Int): Dialog {
        val adb=AlertDialog.Builder(this)
        when(id){
            DIALOG_ITEMS->{
                adb.setTitle(R.string.items)
                adb.setSingleChoiceItems(data,-1,myClickListener)
            }
            DIALOG_ADAPTER->{
                adb.setTitle(R.string.adapter)
                val adapter=ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice,data)
                adb.setSingleChoiceItems(adapter,-1,myClickListener)
            }
            DIALOG_CURSOR->{
                adb.setTitle(R.string.cursor)
                adb.setSingleChoiceItems(cursor,-1,DB.COLUMN_TXT,myClickListener)
            }
        }
        adb.setPositiveButton(R.string.ok,myClickListener)
        return adb.create()
    }

    val myClickListener=DialogInterface.OnClickListener{dialog,which->
        val lv=(dialog as AlertDialog).listView
        if(which==Dialog.BUTTON_POSITIVE)
            Log.d(LOG_TAG,"pos = ${lv.checkedItemPosition}")
        else
            Log.d(LOG_TAG,"which = $which")
    }

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }
}