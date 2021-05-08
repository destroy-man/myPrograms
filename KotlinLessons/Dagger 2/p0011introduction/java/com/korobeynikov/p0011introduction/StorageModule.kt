package com.korobeynikov.p0011introduction

import dagger.Module
import dagger.Provides

@Module
class StorageModule {
    @Provides
    fun provideDatabaseHelper():DatabaseHelper{
        return DatabaseHelper()
    }
}