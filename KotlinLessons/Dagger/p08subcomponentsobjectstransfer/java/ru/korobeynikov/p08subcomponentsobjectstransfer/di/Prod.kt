package ru.korobeynikov.p08subcomponentsobjectstransfer.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Prod(val value: String = "")
