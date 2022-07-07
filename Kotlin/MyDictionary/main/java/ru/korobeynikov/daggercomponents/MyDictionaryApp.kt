package ru.korobeynikov.daggercomponents

import android.app.Application

class MyDictionaryApp : Application() {
    val myDictionaryComponent: MyDictionaryComponent = DaggerMyDictionaryComponent.create()
}