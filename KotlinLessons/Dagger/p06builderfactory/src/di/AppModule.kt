package ru.korobeynikov.p06builderfactory.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.res.Resources
import dagger.Module
import dagger.Provides

@Module
class AppModule(
    //Модуль с параметром в основном конструкторе
    //private val context: Context
) {

//Модуль с параметром в основном конструкторе
//    @Provides
//    fun getAppContext(context: Context): Context {
//        return context
//    }

    @Provides
    fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("prefs", MODE_PRIVATE)
    }

    @Provides
    fun getResources(context: Context): Resources {
        return context.resources
    }
}