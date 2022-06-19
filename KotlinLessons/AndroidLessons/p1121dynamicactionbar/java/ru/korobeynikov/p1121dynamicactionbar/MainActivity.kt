package ru.korobeynikov.p1121dynamicactionbar

import android.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val MENU_ID=1
    lateinit var frag1:Fragment
    lateinit var frag2:Fragment
    lateinit var frag:Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        frag1=Fragment1()
        frag=frag1
        frag2=Fragment2()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main,menu)
        menu?.setGroupVisible(R.id.groupVsbl,chbVisible.isChecked)
        if(chbAddDel.isChecked)
            menu?.add(0,MENU_ID,0,R.string.menu_item1)?.setIcon(android.R.drawable.ic_delete)
                ?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS or MenuItem.SHOW_AS_ACTION_WITH_TEXT)
        else
            menu?.removeItem(MENU_ID)
        return true
    }

    fun onClick(view:View){
        when(view.id){
            R.id.chbAddDel,R.id.chbVisible->invalidateOptionsMenu()
            R.id.btnFrag->{
                frag=if(frag==frag1)frag2 else frag1
                fragmentManager.beginTransaction().replace(R.id.cont,frag).commit()
            }
        }
    }
}