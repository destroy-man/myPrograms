package ru.korobeynikov.p13assistedinject.di

import android.app.Activity
import dagger.BindsInstance
import dagger.Subcomponent
import ru.korobeynikov.p13assistedinject.other.OrderRepository
import ru.korobeynikov.p13assistedinject.other.UiHelper

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