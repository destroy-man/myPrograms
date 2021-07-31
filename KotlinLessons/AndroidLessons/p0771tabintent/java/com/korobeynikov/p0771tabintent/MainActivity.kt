package com.korobeynikov.p0771tabintent

import android.app.TabActivity
import android.content.Intent
import android.os.Bundle
import android.widget.TabHost

class MainActivity : TabActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabHost=getTabHost()
        var tabSpec: TabHost.TabSpec
        tabSpec=tabHost.newTabSpec("tag1")
        tabSpec.setIndicator("Вкладка 1")
        tabSpec.setContent(Intent(this,OneActivity::class.java))
        tabHost.addTab(tabSpec)
        tabSpec=tabHost.newTabSpec("tag2")
        tabSpec.setIndicator("Вкладка 2")
        tabSpec.setContent(Intent(this,TwoActivity::class.java))
        tabHost.addTab(tabSpec)
    }
}