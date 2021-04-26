package com.korobeynikov.p0341concurrency

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

class viewModelExample: ViewModel() {

    val mutex=Mutex()
    var i=0

    suspend fun increment(){
        mutex.withLock{
            i++
        }
    }

    private val actorChannel=viewModelScope.actor<Unit>{
        for(e in channel)
            i++
    }

    fun startViewModel(){
        val singleThreadDispatcher=Executors.newSingleThreadExecutor().asCoroutineDispatcher()
        viewModelScope.launch{
            val job1=launch(Dispatchers.Default){
                repeat(100000){
                    actorChannel.send(Unit)
                }
            }
            val job2=launch(Dispatchers.Default){
                repeat(100000){
                    actorChannel.send(Unit)
                }
            }
            job1.join()
            job2.join()
            actorChannel.close()
            log("i = $i")
        }
    }

    private fun log(text:String){
        Log.d("TAG","$text")
    }
}