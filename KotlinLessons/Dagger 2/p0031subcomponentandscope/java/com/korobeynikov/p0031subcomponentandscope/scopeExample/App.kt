package com.korobeynikov.p0031subcomponentandscope.scopeExample

import android.app.Application

class App:Application() {
    companion object{

        private lateinit var component: AppComponent

        fun create(){
            component = DaggerAppComponent.create()
        }

        fun getComponent(): AppComponent {
            return component
        }

    }

    override fun onCreate() {
        super.onCreate()
        create()
    }
}