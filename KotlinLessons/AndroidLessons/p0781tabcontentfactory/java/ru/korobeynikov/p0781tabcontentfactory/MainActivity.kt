package ru.korobeynikov.p0781tabcontentfactory

import android.app.TabActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : TabActivity() {

    val TABS_TAG_1="Tag 1"
    val TABS_TAG_2="Tag 2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var tabSpec:TabHost.TabSpec=tabHost.newTabSpec(TABS_TAG_1)
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
            if(tag==TABS_TAG_1)
                return layoutInflater.inflate(R.layout.tab,null)
            else if(tag==TABS_TAG_2){
                val tv=TextView(this@MainActivity)
                tv.text="Это создано вручную"
                return tv
            }
            return null
        }
    }
}