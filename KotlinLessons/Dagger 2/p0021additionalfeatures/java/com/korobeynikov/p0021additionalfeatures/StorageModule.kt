package com.korobeynikov.p0021additionalfeatures

import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
import dagger.multibindings.IntoMap
import dagger.multibindings.IntoSet
import dagger.multibindings.StringKey
import java.util.*
import javax.inject.Named
import kotlin.collections.HashSet

@Module
class StorageModule {

    @Provides
    @IntoMap
    @StringKey("default")
    fun provideDatabaseUtilsDefault():DatabaseUtils{
        return DatabaseUtils("default.db")
    }

    @Provides
    @IntoMap
    @StringKey("prod")
    fun provideDatabaseUtils():DatabaseUtils{
        return DatabaseUtils("database.db")
    }

    @Provides
    @IntoMap
    @StringKey("test")
    fun provideDatabaseUtilsTest():DatabaseUtils{
        return DatabaseUtils("test.db")
    }

}