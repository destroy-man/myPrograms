package ru.korobeynikov.p6hiltcustomcomponent.other

import ru.korobeynikov.p6hiltcustomcomponent.di.MyComponent
import ru.korobeynikov.p6hiltcustomcomponent.di.MyComponentBuilder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyComponentManager @Inject constructor(private val myComponentBuilder: MyComponentBuilder) {

    private var myComponent: MyComponent? = null

    fun create() {
        myComponent = myComponentBuilder.build()
    }

    fun get() = myComponent

    fun destroy() {
        myComponent = null
    }
}