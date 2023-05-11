package ru.korobeynikov.p1051fragmentdynamic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import ru.korobeynikov.p1051fragmentdynamic.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var frag1: Fragment1
    private lateinit var frag2: Fragment2
    private lateinit var fTrans: FragmentTransaction
    private lateinit var chbStack: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        frag1 = Fragment1()
        frag2 = Fragment2()
        chbStack = binding.chbStack
    }

    fun clickButton(v: View) {
        val fragManager = supportFragmentManager
        fTrans = fragManager.beginTransaction()
        when (v.id) {
            R.id.btnAdd -> {
                if (fragManager.findFragmentById(frag1.id) == null
                    && fragManager.findFragmentById(frag2.id) == null) {
                    fTrans.add(R.id.frgmCont, frag1)
                    fTrans.add(R.id.frgmCont, frag2)
                }
            }
            R.id.btnRemove -> fTrans.remove(frag1)
            R.id.btnReplace -> fTrans.replace(R.id.frgmCont, frag2)
        }
        if (chbStack.isChecked)
            fTrans.addToBackStack(null)
        fTrans.commit()
    }
}