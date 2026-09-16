package ru.korobeynikov.p14injectviewmodel.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.korobeynikov.p14injectviewmodel.viewmodels.OrderViewModel
import ru.korobeynikov.p14injectviewmodel.viewmodels.UserViewModel
import ru.korobeynikov.p14injectviewmodel.viewmodels.ViewModelFactory

@Module
interface ViewModelBinderModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(OrderViewModel::class)
    fun bindOrderViewModel(viewModel: OrderViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    fun bindsUserViewModel(viewModel: UserViewModel): ViewModel
}