package ru.korobeynikov.p12multiscope.di.order

import android.app.Activity
import dagger.BindsInstance
import dagger.Subcomponent
import ru.korobeynikov.p12multiscope.UiHelper
import ru.korobeynikov.p12multiscope.di.ActivityScope

@ActivityScope
@OrderScope
@Subcomponent(modules = [OrderModule::class])
interface OrderComponent {

    @Subcomponent.Factory
    interface OrderFactory {
        fun create(@BindsInstance activity: Activity): OrderComponent
    }

    fun getUiHelper(): UiHelper
}