package com.korobeynikov.p0671progressdialog

import android.app.Dialog
import android.app.ProgressDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View

class MainActivity : AppCompatActivity() {

    lateinit var pd:ProgressDialog
    lateinit var h:Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onclick(v:View){
        when(v.id){
            R.id.btnDefault->{
                pd= ProgressDialog(this)
                pd.setTitle("Title")
                pd.setMessage("Message")
                pd.setButton(Dialog.BUTTON_POSITIVE,"OK",DialogInterface.OnClickListener{dialog,which-> })
                pd.show()
            }
            R.id.btnHoriz->{
                pd= ProgressDialog(this)
                pd.setTitle("Title")
                pd.setMessage("Message")
                pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
                pd.max=2148
                pd.isIndeterminate=true
                pd.show()
                h=object:Handler(){
                    override fun handleMessage(msg:Message) {
                        pd.isIndeterminate=false
                        if(pd.progress<pd.max){
                            pd.incrementProgressBy(50)
                            pd.incrementSecondaryProgressBy(75)
                            h.sendEmptyMessageDelayed(0,100)
                        }
                        else
                            pd.dismiss()
                    }
                }
                h.sendEmptyMessageDelayed(0,2000)
            }
        }
    }
}