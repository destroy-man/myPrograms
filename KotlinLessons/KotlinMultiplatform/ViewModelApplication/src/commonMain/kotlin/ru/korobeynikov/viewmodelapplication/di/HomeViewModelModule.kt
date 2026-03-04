package ru.korobeynikov.viewmodelapplication.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.korobeynikov.viewmodelapplication.presentation.HomeViewModel
import ru.korobeynikov.viewmodelapplication.data.SomeRepository

val homeViewModelModule = module {
    single { SomeRepository() }
    viewModel {
        HomeViewModel(repository = get())
    }
}