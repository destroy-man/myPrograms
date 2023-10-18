package ru.korobeynikov.p10coroutinescontext

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.korobeynikov.p10coroutinescontext.databinding.ActivityMainBinding
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        log("scope, ${contextToString(scope.coroutineContext)}")
        scope.launch {
            log("coroutine, level1, ${contextToString(coroutineContext)}")
            launch(Dispatchers.Default) {
                log("coroutine, level2, ${contextToString(coroutineContext)}")
                launch(UserData(1, "name1", 10)) {
                    log("coroutine, level3, ${contextToString(coroutineContext)}")
                }
            }
        }
    }

    private fun contextToString(context: CoroutineContext) = "Job = ${context[Job]}, " +
            "Dispatcher = ${context[ContinuationInterceptor]}, UserData = ${context[UserData]}"

    private fun log(text: String) = Log.d("myLogs", text)
}