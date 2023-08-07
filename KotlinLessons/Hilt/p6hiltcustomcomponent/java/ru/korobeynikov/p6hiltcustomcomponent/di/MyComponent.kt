package ru.korobeynikov.p6hiltcustomcomponent.di

import dagger.hilt.DefineComponent
import dagger.hilt.components.SingletonComponent

@MyScope
@DefineComponent(parent = SingletonComponent::class)
interface MyComponent