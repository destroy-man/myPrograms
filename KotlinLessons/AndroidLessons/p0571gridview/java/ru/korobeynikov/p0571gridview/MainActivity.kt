package ru.korobeynikov.p0571gridview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.GridView
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0571gridview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var gvMain: GridView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val data = arrayOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k')
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        gvMain = binding.gvMain
        gvMain.adapter = ArrayAdapter(this, R.layout.item, R.id.tvText, data)
        adjustGridView()
    }

    private fun adjustGridView() {
        gvMain.numColumns = GridView.AUTO_FIT
        gvMain.columnWidth = 80
        gvMain.verticalSpacing = 5
        gvMain.horizontalSpacing = 5
        gvMain.stretchMode = GridView.STRETCH_SPACING_UNIFORM
    }
}