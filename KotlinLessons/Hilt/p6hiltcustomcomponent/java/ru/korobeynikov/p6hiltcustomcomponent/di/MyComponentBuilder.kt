package ru.korobeynikov.p6hiltcustomcomponent.di

import dagger.hilt.DefineComponent

@DefineComponent.Builder
interface MyComponentBuilder {
    fun build(): MyComponent
}