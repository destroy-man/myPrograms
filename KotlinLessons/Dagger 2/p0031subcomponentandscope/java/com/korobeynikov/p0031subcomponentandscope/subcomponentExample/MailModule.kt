package com.korobeynikov.p0031subcomponentandscope.subcomponentExample

import dagger.Module
import dagger.Provides

@Module
class MailModule(someObject: SomeObject){
    @Provides
    fun provideMail(): Mail {
        return Mail()
    }
}