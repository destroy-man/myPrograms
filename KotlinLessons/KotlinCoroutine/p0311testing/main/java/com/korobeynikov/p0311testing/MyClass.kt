package com.korobeynikov.p0311testing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyClass: ViewModel() {
    suspend fun someMethod():String{
        return coroutineScope{
            println("before launch")
            launch{
                println("launch")
            }
            println("after launch")
            "abc"
        }
    }
}