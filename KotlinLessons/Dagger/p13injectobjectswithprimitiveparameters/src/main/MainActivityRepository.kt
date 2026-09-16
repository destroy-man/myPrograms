package ru.korobeynikov.p13injectobjectswithprimitiveparameters.main

import ru.korobeynikov.p13injectobjectswithprimitiveparameters.di.AppComponent.ServerApiFactory
import javax.inject.Inject

class MainActivityRepository @Inject constructor(private val serverApiFactory: ServerApiFactory)