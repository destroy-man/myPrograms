package ru.korobeynikov.p0551headerfooter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ru.korobeynikov.p0551headerfooter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var header1: HeaderFooterAdapter
    private lateinit var header2: HeaderFooterAdapter
    private lateinit var footer1: HeaderFooterAdapter
    private lateinit var footer2: HeaderFooterAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var concatAdapter: ConcatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.view = this
        header1 = createHeader("header 1")
        header2 = createHeader("header 2")
        footer1 = createFooter("footer 1")
        footer2 = createFooter("footer 2")
        fillList()
    }

    private fun fillList() {
        val data = arrayOf("one", "two", "three", "four", "five")
        val rvMain = binding.rvMain
        val layoutManager = LinearLayoutManager(this)
        rvMain.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(rvMain.context, layoutManager.orientation)
        rvMain.addItemDecoration(dividerItemDecoration)
        concatAdapter = ConcatAdapter(header1, header2, NumberAdapter(data), footer1, footer2)
        rvMain.adapter = concatAdapter
    }

    fun clickButton() {
        concatAdapter.removeAdapter(header2)
        concatAdapter.removeAdapter(footer2)
    }

    private fun createHeader(text: String): HeaderFooterAdapter {
        return HeaderFooterAdapter(text, true)
    }

    private fun createFooter(text: String): HeaderFooterAdapter {
        return HeaderFooterAdapter(text, false)
    }
}