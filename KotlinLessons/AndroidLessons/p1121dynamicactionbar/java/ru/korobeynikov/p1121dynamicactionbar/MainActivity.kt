package ru.korobeynikov.p1121dynamicactionbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import ru.korobeynikov.p1121dynamicactionbar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val menuId = 1
    private lateinit var chbAddDel: CheckBox
    private lateinit var chbVisible: CheckBox
    private lateinit var frag1: Fragment
    private lateinit var frag2: Fragment
    private lateinit var frag: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        chbAddDel = binding.chbAddDel
        chbVisible = binding.chbVisible
        frag = Fragment1()
        frag1 = frag
        frag2 = Fragment2()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        menu?.setGroupVisible(R.id.groupVsbl, chbVisible.isChecked)
        if (chbAddDel.isChecked) {
            menu?.add(0, menuId, 0, R.string.menu_item1)?.setIcon(android.R.drawable.ic_delete)
                ?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS or MenuItem.SHOW_AS_ACTION_WITH_TEXT)
        } else
            menu?.removeItem(menuId)
        return true
    }

    fun clickButton(view: View) {
        when (view.id) {
            R.id.chbAddDel, R.id.chbVisible -> invalidateOptionsMenu()
            R.id.btnFrag -> {
                frag =
                    if (frag == frag1) frag2
                    else frag1
                supportFragmentManager.beginTransaction().replace(R.id.cont, frag).commit()
            }
        }
    }
}