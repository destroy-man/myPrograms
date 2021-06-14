package com.korobeynikov.p0601alertdialogsimple

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    val DIALOG_EXIT=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onclick(v:View){
        showDialog(DIALOG_EXIT)
    }

    protected override fun onCreateDialog(id: Int): Dialog {
        if(id==DIALOG_EXIT){
            val adb=AlertDialog.Builder(this)
            adb.setTitle(R.string.exit)
            adb.setMessage(R.string.save_data)
            adb.setIcon(android.R.drawable.ic_dialog_info)
            adb.setPositiveButton(R.string.yes,myClickListener)
            adb.setNegativeButton(R.string.no,myClickListener)
            adb.setNeutralButton(R.string.cancel,myClickListener)
            adb.setCancelable(false)
            return adb.create()
        }
        return super.onCreateDialog(id)
    }

    val myClickListener=DialogInterface.OnClickListener{ dialog, which ->
        when(which){
            Dialog.BUTTON_POSITIVE->{
                saveData()
                finish()
            }
            Dialog.BUTTON_NEGATIVE->{
                finish()
            }
        }
    }

    fun saveData(){
        Toast.makeText(this,R.string.saved,Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        showDialog(DIALOG_EXIT)
    }
}