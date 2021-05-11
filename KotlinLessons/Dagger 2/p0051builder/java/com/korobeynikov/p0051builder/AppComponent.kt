package com.korobeynikov.p0051builder

import dagger.BindsInstance
import dagger.Component

@Component(modules=[AppModule::class])
interface AppComponent {
    fun getSomeObject():SomeObject

    @Component.Builder
    interface MyBuilder{
        fun letsBuildThisComponent():AppComponent
        @BindsInstance
        fun setMyInstanceOfSomeObject(someObject:SomeObject):MyBuilder
    }
}