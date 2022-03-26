package ru.korobeynikov.p0671progressdialog

import android.app.Dialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
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
                pd=ProgressDialog(this)
                pd.setTitle("Title")
                pd.setMessage("Message")
                pd.setButton(Dialog.BUTTON_POSITIVE,"OK", { dialogInterface, i -> })
                pd.show()
            }
            R.id.btnHoriz->{
                pd=ProgressDialog(this)
                pd.setTitle("Title")
                pd.setMessage("Message")
                pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
                pd.max=2148
                pd.isIndeterminate=true
                pd.show()
                h=Handler(){
                    pd.isIndeterminate=false
                    if(pd.progress<pd.max){
                        pd.incrementProgressBy(50)
                        pd.incrementSecondaryProgressBy(75)
                        h.sendEmptyMessageDelayed(0,100)
                    }
                    else
                        pd.dismiss()
                    false
                }
                h.sendEmptyMessageDelayed(0,2000)
            }
        }
    }
}