package com.korobeynikov.p0101context

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userData=UserData(1,"name1",10)
        val scope=CoroutineScope(Job()+Dispatchers.Main+userData)
        Log.d("TAG","scope, ${contextToString(scope.coroutineContext)}")
        scope.launch{
            Log.d("TAG","coroutine, level1, ${contextToString(coroutineContext)}")
            launch(Dispatchers.Default){
                Log.d("TAG","coroutine, level2, ${contextToString(coroutineContext)}")
                launch{
                    Log.d("TAG","coroutine, level3, ${contextToString(coroutineContext)}")
                }
            }
        }
    }

    private fun contextToString(context:CoroutineContext):
            String="Job = ${context[Job]}, Dispatcher = ${context[ContinuationInterceptor]}"
}