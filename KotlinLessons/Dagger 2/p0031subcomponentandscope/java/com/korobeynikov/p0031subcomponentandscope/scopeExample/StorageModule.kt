package com.korobeynikov.p0031subcomponentandscope.scopeExample

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {
    @PerApplication
    @Provides
    fun provideDatabaseHelper():DatabaseHelper{
        return DatabaseHelper()
    }
}