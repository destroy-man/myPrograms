package ru.korobeynikov.p1hiltunderthehood.di

import dagger.Subcomponent
import ru.korobeynikov.p1hiltunderthehood.other.OrderFragment

@Subcomponent
interface HiltFragmentComponent {
    fun injectOrderFragment(fragment: OrderFragment)
}