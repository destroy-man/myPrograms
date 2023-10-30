package ru.korobeynikov.p24coroutinesretrofit

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.korobeynikov.p24coroutinesretrofit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://rawgit.com/startandroid/data/master/messages/").build()
        val messages = MutableLiveData<List<Message>>()
        CoroutineScope(Job()).launch {
            try {
                val response = retrofit.create(WebApi::class.java).messages(1)
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        messages.value = response.body()
                        log("onResponse, messages count ${messages.value?.size}")
                    }
                } else
                    log("HTTP error: ${response.code()}")
            } catch (e: Exception) {
                log("error $e")
            }
        }
    }

    private fun log(text: String) = Log.d("myLogs", text)
}