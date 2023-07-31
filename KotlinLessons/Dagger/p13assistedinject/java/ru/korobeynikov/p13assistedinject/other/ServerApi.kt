package ru.korobeynikov.p13assistedinject.other

import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class ServerApi @AssistedInject constructor(
    val networkUtils: NetworkUtils,
    @Assisted("host") val host: String,
    @Assisted("port") val port: String
)