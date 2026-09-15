package ru.korobeynikov.p12multiscope.di

import dagger.Component
import ru.korobeynikov.p12multiscope.OrderActivity
import ru.korobeynikov.p12multiscope.UserActivity
import ru.korobeynikov.p12multiscope.di.order.OrderComponent.OrderFactory
import ru.korobeynikov.p12multiscope.di.user.UserComponent.UserFactory

@Component(modules = [StorageModule::class])
interface AppComponent {

    //MultiScope для get-методов
    //fun getOrderFactory(): OrderFactory

    //fun getUserFactory(): UserFactory

    fun injectOrderActivity(orderActivity: OrderActivity)

    fun injectUserActivity(userActivity: UserActivity)
}