package com.korobeynikov.p0231scopelivedata

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    init{
        log("launch")
        viewModelScope.launch{
            while(true){
                delay(1000)
                log("work")
            }
        }
    }

    private fun log(text:String){
        Log.d("TAG","$text")
    }
}