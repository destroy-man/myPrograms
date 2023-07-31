package ru.korobeynikov.p13assistedinject.other

import ru.korobeynikov.p13assistedinject.di.ServerApiFactory
import javax.inject.Inject

class MainActivityPresenter @Inject constructor(serverApiFactory: ServerApiFactory) {
    val serverApi = serverApiFactory.create("dev1.server.com")
}