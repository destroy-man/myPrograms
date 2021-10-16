package com.korobeynikov.p1101dialogfragment

import android.app.DialogFragment
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class Dialog1:DialogFragment(),View.OnClickListener {

    val LOG_TAG="myLogs"

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.setTitle("Title!")
        val v=inflater?.inflate(R.layout.dialog1,null)
        v?.findViewById<Button>(R.id.btnYes)?.setOnClickListener(this)
        v?.findViewById<Button>(R.id.btnNo)?.setOnClickListener(this)
        v?.findViewById<Button>(R.id.btnMaybe)?.setOnClickListener(this)
        return v
    }

    override fun onClick(v: View?) {
        Log.d(LOG_TAG,"Dialog 1: "+(v as Button).text)
        dismiss()
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        Log.d(LOG_TAG,"Dialog 1: onDismiss")
    }

    override fun onCancel(dialog: DialogInterface?) {
        super.onCancel(dialog)
        Log.d(LOG_TAG,"Dialog 1: onCancel")
    }
}