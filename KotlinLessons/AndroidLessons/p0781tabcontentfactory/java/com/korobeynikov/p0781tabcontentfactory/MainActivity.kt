package com.korobeynikov.p0781tabcontentfactory

import android.app.TabActivity
import android.os.Bundle
import android.view.View
import android.widget.TabHost
import android.widget.TextView

class MainActivity : TabActivity() {

    val TABS_TAG_1="Tag 1"
    val TABS_TAG_2="Tag 2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabHost=getTabHost()
        var tabSpec:TabHost.TabSpec
        tabSpec=tabHost.newTabSpec(TABS_TAG_1)
        tabSpec.setContent(tabFactory)
        tabSpec.setIndicator("Вкладка 1")
        tabHost.addTab(tabSpec)
        tabSpec=tabHost.newTabSpec(TABS_TAG_2)
        tabSpec.setContent(tabFactory)
        tabSpec.setIndicator("Вкладка 2")
        tabHost.addTab(tabSpec)
    }

    val tabFactory=object:TabHost.TabContentFactory{
        override fun createTabContent(tag: String?): View? {
            if(tag.equals(TABS_TAG_1))
                return layoutInflater.inflate(R.layout.tab,null)
            else if(tag.equals(TABS_TAG_2)){
                val tv=TextView(this@MainActivity)
                tv.text="Это создано вручную"
                return tv
            }
            return null
        }
    }
}