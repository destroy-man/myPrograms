package ru.korobeynikov.p34coroutinesconcurrency

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.launch
import ru.korobeynikov.p34coroutinesconcurrency.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val scope = CoroutineScope(Job())
    private var i = 0

    @OptIn(ObsoleteCoroutinesApi::class)
    private val actorChannel = scope.actor<Unit> {
        for (e in channel) {
            i++
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        scope.launch {
            val job1 = launch {
                repeat(100000) {
                    actorChannel.send(Unit)
                }
            }
            val job2 = launch {
                repeat(100000) {
                    actorChannel.send(Unit)
                }
            }
            job1.join()
            job2.join()
            actorChannel.close()
            log("i = $i")
        }
    }

    private fun log(text: String) = Log.d("myLogs", text)
}