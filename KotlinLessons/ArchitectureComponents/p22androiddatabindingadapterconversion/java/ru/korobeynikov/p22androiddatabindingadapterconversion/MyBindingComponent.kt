package ru.korobeynikov.p22androiddatabindingadapterconversion

import androidx.databinding.DataBindingComponent

class MyBindingComponent : DataBindingComponent {
    override fun getBindingAdapters(): BindingAdapters {
        return BindingAdapters()
    }
}