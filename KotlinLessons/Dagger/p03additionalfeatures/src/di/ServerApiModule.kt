package ru.korobeynikov.p03additionalfeatures.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p03additionalfeatures.ServerApi
import ru.korobeynikov.p03additionalfeatures.di.qualifier.Dev
import ru.korobeynikov.p03additionalfeatures.di.qualifier.Prod

@Module
class ServerApiModule {

    /*Named
    @Named("prod")
    @Provides
    fun provideServerApiProd(): ServerApi {
        return ServerApi("prod.server.com")
    }
     */

    //Qualifier
    @Prod("1")
    @Provides
    fun provideServerApiProd1(): ServerApi {
        return ServerApi("prod1.server.com")
    }

    //Qualifier
    @Prod("2")
    @Provides
    fun provideServerApiProd2(): ServerApi {
        return ServerApi("prod2.server.com")
    }

    //Named
    //@Named("dev")
    //Qualifier
    @Dev
    @Provides
    fun provideServerApiDev(): ServerApi {
        return ServerApi("dev.server.com")
    }
}