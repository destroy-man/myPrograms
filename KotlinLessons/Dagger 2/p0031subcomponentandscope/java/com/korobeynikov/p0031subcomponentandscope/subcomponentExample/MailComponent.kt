package com.korobeynikov.p0031subcomponentandscope.subcomponentExample

import dagger.Subcomponent

@Subcomponent(modules=[MailModule::class])
interface MailComponent{
}