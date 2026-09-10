package ru.korobeynikov.p11scope.order

import ru.korobeynikov.p11scope.di.scope.AppScope
import ru.korobeynikov.p11scope.di.scope.OrderScope
import javax.inject.Inject

//@OrderScope будет жить пока живет OrderActivity
@AppScope //будет жить пока живет приложение
class OrderRepository @Inject constructor()