package ru.korobeynikov.p0541customadapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ru.korobeynikov.p0541customadapter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val products = Array<Product?>(20) { null }
    private lateinit var boxAdapter: BoxAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
        fillData()
        val rvMain = binding.rvMain
        val layoutManager = LinearLayoutManager(this)
        rvMain.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(rvMain.context, layoutManager.orientation)
        rvMain.addItemDecoration(dividerItemDecoration)
        boxAdapter = BoxAdapter((products))
        rvMain.adapter = boxAdapter
    }

    private fun fillData() {
        for (i in 1..20)
            products[i - 1] = Product("Product $i", i * 1000, false)
    }

    fun showResult() {
        val result = StringBuilder("Товары в корзине:")
        for (p in boxAdapter.getBox())
            if (p != null && p.box)
                result.append("\n${p.name}")
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
    }
}