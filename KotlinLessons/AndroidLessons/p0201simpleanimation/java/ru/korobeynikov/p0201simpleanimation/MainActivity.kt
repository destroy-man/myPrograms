package ru.korobeynikov.p0201simpleanimation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0201simpleanimation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val menuAlphaId = 1
    private val menuScaleId = 2
    private val menuTranslateId = 3
    private val menuRotateId = 4
    private val menuComboId = 5
    private lateinit var tv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        tv = binding.tv
        registerForContextMenu(tv)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        when (v?.id) {
            R.id.tv -> {
                menu?.add(0, menuAlphaId, 0, "alpha")
                menu?.add(0, menuScaleId, 0, "scale")
                menu?.add(0, menuTranslateId, 0, "translate")
                menu?.add(0, menuRotateId, 0, "rotate")
                menu?.add(0, menuComboId, 0, "combo")
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        var anim: Animation? = null
        when (item.itemId) {
            menuAlphaId -> anim = AnimationUtils.loadAnimation(this, R.anim.myalpha)
            menuScaleId -> anim = AnimationUtils.loadAnimation(this, R.anim.myscale)
            menuTranslateId -> anim = AnimationUtils.loadAnimation(this, R.anim.mytrans)
            menuRotateId -> anim = AnimationUtils.loadAnimation(this, R.anim.myrotate)
            menuComboId -> anim = AnimationUtils.loadAnimation(this, R.anim.mycombo)
        }
        tv.startAnimation(anim)
        return super.onContextItemSelected(item)
    }
}