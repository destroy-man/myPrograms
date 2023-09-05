package ru.korobeynikov.p02livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object DataController {

    private val liveData = MutableLiveData<String>()

    fun getData(): LiveData<String> {
        liveData.value = "new value"
        return liveData
    }
}