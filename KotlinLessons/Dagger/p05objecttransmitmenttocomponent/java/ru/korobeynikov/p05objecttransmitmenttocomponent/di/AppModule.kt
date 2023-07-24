package ru.korobeynikov.p05objecttransmitmenttocomponent.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.res.Resources
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val context: Context) {

    @Provides
    fun getPreferences(): SharedPreferences = context.getSharedPreferences("prefs", MODE_PRIVATE)

    @Provides
    fun getResources(): Resources = context.resources

    @Provides
    fun getAppContext() = context
}