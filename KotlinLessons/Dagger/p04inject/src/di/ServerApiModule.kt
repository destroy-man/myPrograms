package ru.korobeynikov.p04inject.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p04inject.ServerApi
import javax.inject.Named

@Module
class ServerApiModule {

    @Named("prod")
    @Provides
    fun provideServerApiProd(): ServerApi {
        return ServerApi("prod.server.com")
    }

    @Dev
    @Provides
    fun provideServerApiDev(): ServerApi {
        return ServerApi("dev.server.com")
    }
}