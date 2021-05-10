package com.korobeynikov.p0031subcomponentandscope.scopeExample

import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PerApplication {
}