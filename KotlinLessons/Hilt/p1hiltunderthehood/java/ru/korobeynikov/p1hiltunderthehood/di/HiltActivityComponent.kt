package ru.korobeynikov.p1hiltunderthehood.di

import dagger.Subcomponent
import ru.korobeynikov.p1hiltunderthehood.other.OrderActivity
import ru.korobeynikov.p1hiltunderthehood.other.UserActivity

@Subcomponent
interface HiltActivityComponent {

    fun injectOrderActivity(activity: OrderActivity)

    fun injectUserActivity(activity: UserActivity)

    fun getFragmentComponent(): HiltFragmentComponent
}