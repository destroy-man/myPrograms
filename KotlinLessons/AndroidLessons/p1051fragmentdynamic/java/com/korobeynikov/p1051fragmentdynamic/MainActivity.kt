package com.korobeynikov.p1051fragmentdynamic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var frag1:Fragment1
    lateinit var frag2:Fragment2
    lateinit var fTrans:FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        frag1=Fragment1()
        frag2= Fragment2()
    }

    fun onClick(v:View){
        fTrans=supportFragmentManager.beginTransaction()
        when(v.id){
            R.id.btnAdd->fTrans.add(R.id.frgmCont,frag1)
            R.id.btnRemove->fTrans.remove(frag1)
            R.id.btnReplace->fTrans.replace(R.id.frgmCont,frag2)
        }
        if(chbStack.isChecked)fTrans.addToBackStack(null)
        fTrans.commit()
    }
}