package ru.korobeynikov.p04annotationinject.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Prod(val value: String = "")
