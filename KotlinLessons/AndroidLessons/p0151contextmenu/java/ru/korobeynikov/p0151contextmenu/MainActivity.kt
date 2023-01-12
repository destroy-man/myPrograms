package ru.korobeynikov.p0151contextmenu

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0151contextmenu.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var tvColor: TextView
    private lateinit var tvSize: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        tvColor = binding.tvColor
        tvSize = binding.tvSize
        registerForContextMenu(tvColor)
        registerForContextMenu(tvSize)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        when (v?.id) {
            R.id.tvColor -> menuInflater.inflate(R.menu.colors, menu)
            R.id.tvSize -> menuInflater.inflate(R.menu.sizes, menu)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.color_red -> {
                tvColor.setTextColor(Color.RED)
                tvColor.text = resources.getText(R.string.clickItemRed)
            }
            R.id.color_green -> {
                tvColor.setTextColor(Color.GREEN)
                tvColor.text = resources.getText(R.string.clickItemGreen)
            }
            R.id.color_blue -> {
                tvColor.setTextColor(Color.BLUE)
                tvColor.text = resources.getText(R.string.clickItemBlue)
            }
            R.id.size_22 -> {
                tvSize.textSize = 22f
                tvSize.text = resources.getText(R.string.clickItem22)
            }
            R.id.size_26 -> {
                tvSize.textSize = 26f
                tvSize.text = resources.getText(R.string.clickItem26)
            }
            R.id.size_30 -> {
                tvSize.textSize = 30f
                tvSize.text = resources.getText(R.string.clickItem30)
            }
        }
        return super.onContextItemSelected(item)
    }
}