package ru.korobeynikov.p12multiscope.di

import android.app.Activity
import dagger.BindsInstance
import dagger.Subcomponent
import ru.korobeynikov.p12multiscope.other.OrderRepository
import ru.korobeynikov.p12multiscope.other.UiHelper

@ActivityScope
@OrderScope
@Subcomponent(modules = [OrderModule::class])
interface OrderComponent {

    fun getOrderRepository(): OrderRepository

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance activity: Activity): OrderComponent
    }

    fun getUiHelper(): UiHelper
}