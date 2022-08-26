package ru.korobeynikov.databasegames

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainViewModelModule = module {
    single { DBGames(get()) }
    single { MainModel(get()) }
    single { CoroutineScope(Job()) }
    viewModel { MainViewModel(get(), get()) }
}