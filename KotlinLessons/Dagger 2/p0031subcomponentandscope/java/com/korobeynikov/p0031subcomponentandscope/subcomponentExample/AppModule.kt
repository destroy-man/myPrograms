package com.korobeynikov.p0031subcomponentandscope.subcomponentExample

import dagger.Module
import dagger.Provides

@Module
class AppModule(someObject: SomeObject) {
    @Provides
    fun provideApp(): Appl {
        return Appl()
    }
}