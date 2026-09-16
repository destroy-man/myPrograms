package ru.korobeynikov.p14injectviewmodel.di.viewmodel

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p14injectviewmodel.Utils
import ru.korobeynikov.p14injectviewmodel.database.DatabaseHelper
import ru.korobeynikov.p14injectviewmodel.viewmodels.OrderViewModel
import ru.korobeynikov.p14injectviewmodel.viewmodels.UserViewModel

@Module
class ViewModelProviderModule {

    @Provides
    fun provideOrderViewModel(utils: Utils): OrderViewModel {
        return OrderViewModel(utils)
    }

    @Provides
    fun provideUserViewModel(databaseHelper: DatabaseHelper): UserViewModel {
        return UserViewModel(databaseHelper)
    }
}