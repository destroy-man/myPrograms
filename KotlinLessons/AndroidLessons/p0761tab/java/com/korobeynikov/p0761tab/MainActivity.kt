package com.korobeynikov.p0761tab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TabHost
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabhost.setup()
        var tabSpec:TabHost.TabSpec
        tabSpec=tabhost.newTabSpec("tag1")
        tabSpec.setIndicator("Вкладка 1")
        tabSpec.setContent(R.id.tvTab1)
        tabhost.addTab(tabSpec)
        tabSpec=tabhost.newTabSpec("tag2")
        tabSpec.setIndicator("Вкладка 2",resources.getDrawable(R.drawable.tab_icon_selector))
        tabSpec.setContent(R.id.tvTab2)
        tabhost.addTab(tabSpec)
        tabSpec=tabhost.newTabSpec("tag3")
        val v=layoutInflater.inflate(R.layout.tab_header,null)
        tabSpec.setIndicator(v)
        tabSpec.setContent(R.id.tvTab3)
        tabhost.addTab(tabSpec)
        tabhost.setCurrentTabByTag("tag2")
        tabhost.setOnTabChangedListener{
            Toast.makeText(baseContext,"tabId = "+it,Toast.LENGTH_SHORT).show()
        }
    }
}