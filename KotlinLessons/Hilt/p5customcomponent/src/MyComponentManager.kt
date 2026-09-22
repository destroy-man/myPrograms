package ru.korobeynikov.p5customcomponent

import ru.korobeynikov.p5customcomponent.di.MyComponent
import ru.korobeynikov.p5customcomponent.di.MyComponentBuilder
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