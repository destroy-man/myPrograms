package ru.korobeynikov.p0151contextmenu

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val MENU_COLOR_RED=1
    val MENU_COLOR_GREEN=2
    val MENU_COLOR_BLUE=3
    val MENU_SIZE_22=4
    val MENU_SIZE_26=5
    val MENU_SIZE_30=6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerForContextMenu(tvColor)
        registerForContextMenu(tvSize)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        when(v?.id){
            R.id.tvColor->{
                menuInflater.inflate(R.menu.menu_color,menu)
            }
            R.id.tvSize->{
                menuInflater.inflate(R.menu.menu_size,menu)
            }
        }
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_color_red->{
                tvColor.setTextColor(Color.RED)
                tvColor.text="Text color = red"
            }
            R.id.menu_color_green->{
                tvColor.setTextColor(Color.GREEN)
                tvColor.text="Text color = green"
            }
            R.id.menu_color_blue->{
                tvColor.setTextColor(Color.BLUE)
                tvColor.text="Text color = blue"
            }
            R.id.menu_size_22->{
                tvSize.textSize=22F
                tvSize.text="Text size = 22"
            }
            R.id.menu_size_26->{
                tvSize.textSize=26F
                tvSize.text="Text size = 26"
            }
            R.id.menu_size_30->{
                tvSize.textSize=30F
                tvSize.text="Text size = 30"
            }
        }
        return super.onContextItemSelected(item)
    }
}