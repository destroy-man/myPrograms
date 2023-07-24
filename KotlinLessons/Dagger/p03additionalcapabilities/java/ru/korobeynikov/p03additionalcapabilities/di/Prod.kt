package ru.korobeynikov.p03additionalcapabilities.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Prod(val value: String = "")
