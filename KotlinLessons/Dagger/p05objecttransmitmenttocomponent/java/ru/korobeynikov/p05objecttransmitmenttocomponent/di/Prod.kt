package ru.korobeynikov.p05objecttransmitmenttocomponent.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Prod(val value: String = "")
