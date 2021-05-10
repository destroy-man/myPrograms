package com.korobeynikov.p0031subcomponentandscope.scopeExample

import com.korobeynikov.p0031subcomponentandscope.subcomponentExample.AppModule
import com.korobeynikov.p0031subcomponentandscope.subcomponentExample.MailComponent
import com.korobeynikov.p0031subcomponentandscope.subcomponentExample.MailModule
import dagger.Component
import javax.inject.Singleton

@PerApplication
@Component(modules=[StorageModule::class,NetworkModule::class])
interface AppComponent {
    fun getNetworkUtils():NetworkUtils
    fun getDatabaseHelper():DatabaseHelper
}