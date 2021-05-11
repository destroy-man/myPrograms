package com.korobeynikov.p0041producers

import com.google.common.util.concurrent.ListenableFuture
import dagger.producers.ProductionComponent

@ProductionComponent(modules=[UserDataModule::class,ExecutorModule::class])
interface UserComponent {
    fun getUserData():ListenableFuture<UserData>
}