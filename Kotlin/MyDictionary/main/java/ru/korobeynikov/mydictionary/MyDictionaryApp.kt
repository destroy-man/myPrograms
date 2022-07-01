package ru.korobeynikov.mydictionary

import android.app.Application

class MyDictionaryApp : Application() {
    val myDictionaryComponent: MyDictionaryComponent = DaggerMyDictionaryComponent.create()
}