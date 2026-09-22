package ru.korobeynikov.p5customcomponent.di

import dagger.hilt.DefineComponent
import dagger.hilt.components.SingletonComponent

@MyScope
@DefineComponent(parent = SingletonComponent::class)
interface MyComponent