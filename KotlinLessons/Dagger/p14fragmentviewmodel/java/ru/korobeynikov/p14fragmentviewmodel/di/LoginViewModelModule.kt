package ru.korobeynikov.p14fragmentviewmodel.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p14fragmentviewmodel.other.LoginViewModel

@Module
class LoginViewModelModule {
    @Provides
    fun provideLoginViewModel() = LoginViewModel()
}