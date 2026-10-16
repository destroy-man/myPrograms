package ru.korobeynikov.p04viewmodel.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel

class ViewModelWithContext(application: Application) : AndroidViewModel(application) {
    fun getContext(): Context {
        val context = getApplication<Application>()
        return context
    }
}