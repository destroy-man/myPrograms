package ru.korobeynikov.p14fragmentviewmodel.di

import android.app.Activity
import dagger.Component

@Component(modules = [LoginViewModelModule::class])
interface LoginComponent {
    fun inject(activity: Activity)
}