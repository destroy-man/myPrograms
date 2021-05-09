package com.korobeynikov.p0021additionalfeatures

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.Lazy
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mDatabaseUtils: Map<String,DatabaseUtils>
    
    @Inject
    lateinit var mDatabaseUtilsTest: Map<String,DatabaseUtils>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        App.getComponent().injectsMainActivity(this)
    }
}