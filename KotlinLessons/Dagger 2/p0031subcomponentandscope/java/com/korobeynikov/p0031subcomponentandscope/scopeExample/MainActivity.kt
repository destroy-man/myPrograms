package com.korobeynikov.p0031subcomponentandscope.scopeExample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.korobeynikov.p0031subcomponentandscope.R
import com.korobeynikov.p0031subcomponentandscope.subcomponentExample.MailComponent
import com.korobeynikov.p0031subcomponentandscope.subcomponentExample.MailModule
import com.korobeynikov.p0031subcomponentandscope.subcomponentExample.SomeObject

class MainActivity : AppCompatActivity() {

    lateinit var networkUtils: NetworkUtils
    lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        networkUtils=App.getComponent().getNetworkUtils()
        databaseHelper=App.getComponent().getDatabaseHelper()
    }
}