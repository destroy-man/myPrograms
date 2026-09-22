package ru.korobeynikov.p5customcomponent.di

import dagger.hilt.DefineComponent

@DefineComponent.Builder
interface MyComponentBuilder {
    fun build(): MyComponent
}