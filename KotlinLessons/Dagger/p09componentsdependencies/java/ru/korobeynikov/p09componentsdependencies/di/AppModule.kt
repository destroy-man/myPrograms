package ru.korobeynikov.p09componentsdependencies.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.res.Resources
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun getPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences("prefs", MODE_PRIVATE)

    @Provides
    fun getResources(context: Context): Resources = context.resources
}