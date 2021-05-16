package com.korobeynikov.p0191simplecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),View.OnClickListener {

    var oper=""
    val MENU_RESET_ID=1
    val MENU_QUIT_ID=2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAdd.setOnClickListener(this)
        btnSub.setOnClickListener(this)
        btnMult.setOnClickListener(this)
        btnDiv.setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add(0,MENU_RESET_ID,0,"Reset")
        menu?.add(0,MENU_QUIT_ID,0,"Quit")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            MENU_RESET_ID->{
                etNum1.setText("")
                etNum2.setText("")
                tvResult.setText("")
            }
            MENU_QUIT_ID->finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        var num1=0F
        var num2=0F
        var result=0F
        if(TextUtils.isEmpty(""+etNum1.text) || TextUtils.isEmpty(""+etNum2.text))
            return
        num1=etNum1.text.toString().toFloat()
        num2=etNum2.text.toString().toFloat()
        when(v?.id){
            R.id.btnAdd->{
                oper="+"
                result=num1+num2
            }
            R.id.btnSub->{
                oper="-"
                result=num1-num2
            }
            R.id.btnMult->{
                oper="*"
                result=num1*num2
            }
            R.id.btnDiv->{
                if(num2==0F){
                    tvResult.text="На ноль делить нельзя!"
                    return
                }
                oper="/"
                result=num1/num2
            }
        }
        tvResult.text=""+num1+" "+oper+" "+num2+" = "+result
    }
}