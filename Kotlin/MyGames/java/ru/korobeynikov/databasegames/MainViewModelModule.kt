package ru.korobeynikov.databasegames

import androidx.lifecycle.MutableLiveData
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainViewModelModule = module {
    single { DBGames(get()) }
    single { MainModel(get()) }
    single { MutableLiveData<List<String>>() }
    viewModel { MainViewModel(get(), get()) }
}