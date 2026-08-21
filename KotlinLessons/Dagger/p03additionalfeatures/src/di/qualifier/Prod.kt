package ru.korobeynikov.p03additionalfeatures.di.qualifier

import javax.inject.Qualifier

@Qualifier
annotation class Prod(val value: String = "")