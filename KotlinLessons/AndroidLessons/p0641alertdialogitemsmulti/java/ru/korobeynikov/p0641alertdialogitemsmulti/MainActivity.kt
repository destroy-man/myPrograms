package ru.korobeynikov.p0641alertdialogitemsmulti

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity() {

    val LOG_TAG="myLogs"
    val DIALOG_ITEMS=1
    val DIALOG_CURSOR=3
    lateinit var db:DB
    lateinit var cursor:Cursor
    val data=arrayOf("one","two","three","four")
    val chkd=arrayOf(false,true,true,false)

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
            R.id.btnCursor->showDialog(DIALOG_CURSOR)
        }
    }

    override fun onCreateDialog(id: Int): Dialog {
        val adb=AlertDialog.Builder(this)
        when(id){
            DIALOG_ITEMS->{
                adb.setTitle(R.string.items)
                adb.setMultiChoiceItems(data,chkd.toBooleanArray(),myItemsMultiCheckListener)
            }
            DIALOG_CURSOR->{
                adb.setTitle(R.string.cursor)
                adb.setMultiChoiceItems(cursor,DB.COLUMN_CHK,DB.COLUMN_TXT,myCursorMultiCheckListener)
            }
        }
        adb.setPositiveButton(R.string.ok,myBtnClickListener)
        return adb.create()
    }

    val myItemsMultiCheckListener=DialogInterface.OnMultiChoiceClickListener{dialog,which,isChecked->
        Log.d(LOG_TAG,"which = $which, isChecked = $isChecked")
    }
    val myCursorMultiCheckListener=DialogInterface.OnMultiChoiceClickListener{dialog,which,isChecked->
        val lv=(dialog as AlertDialog).listView
        Log.d(LOG_TAG,"which = $which, isChecked = $isChecked")
        db.changeRec(which,isChecked)
        cursor.requery()
    }
    val myBtnClickListener=DialogInterface.OnClickListener{dialog,which->
        val sbArray=(dialog as AlertDialog).listView.checkedItemPositions
        for(i in 0..sbArray.size()-1){
            val key=sbArray.keyAt(i)
            if(sbArray.get(key))
                Log.d("qwe","checked: $key")
        }
    }

    protected override fun onDestroy() {
        super.onDestroy()
        db.close()
    }
}