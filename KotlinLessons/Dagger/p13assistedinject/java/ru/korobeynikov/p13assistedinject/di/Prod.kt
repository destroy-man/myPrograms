package ru.korobeynikov.p13assistedinject.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Prod(val value: String = "")
